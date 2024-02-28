import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';
import { zfd } from 'zod-form-data';
import {z} from 'zod';
import {fail} from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, locals }) {
  const { id } = params;

  if (!locals.user) throw redirect(302, `/login`);

  //Wenn der JWT Token abgelaufen ist, wird der Nutzer automatisch abgemeldet
  if (new Date() > new Date(locals.user.expires_at)) {
    cookies.delete('jwt', { path: '/' });
    locals.user = null;

    throw redirect(300, '/');
  }

  const modules = await api.get('modules');
  const request = await api.get(`requests/relatedRequests/${id}`);
  // const request = await api.get(`requests/${uuid}`)

  if (!request || request == 500 || request == 404) {
    throw error(404, { message: 'Antrag existiert nicht' });
  }

  return {
    modules: modules,
    request: request,
    user: locals.user,
    title: 'Antrag bearbeiten'
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  changeRequest: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      // status: zfd.text(),
      annotation: zfd.text(),
      internalModuleId: zfd.text()
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      return fail(400, { errors: 'keine ID angegeben' });
    }

    const res = await api.put(`procedures/${result.data}`, locals.user?.token, { res_type: api.content_type.plain });

    return { success: true };
  },
  updateRequest: async ({ locals, request }) => {
    const formData = await request.formData();
  
    const schema = zfd.formData({
      requestId: zfd.text().transform((value) => {
        const parsed = parseInt(value, 10);
        if (isNaN(parsed)) {
          throw new Error("requestId must be a valid integer");
        }
        return parsed;
      }),
      externalModuleId: zfd.text(),
      internalModuleId: zfd.text(),
      annotation: zfd.text(),
      creditPoints: zfd.text().transform((value) => parseInt(value, 10)),
      status: zfd.text(z.string({ required_error: 'Status darf nicht leer sein' })),
      createdAt: zfd.text(),
      pdfExists: zfd.text(), //.transform((value) => value === 'true'), // Vielleicht sinnvoller in +page.svelte zu handlen?
      moduleLink: zfd.text().optional(),
    });
  
    const result = schema.safeParse(formData);
    console.log(result.success);
    if (!result.success) {
      const data = {
        data: Object.fromEntries(formData),
        errors: result.error.flatten().fieldErrors
      };
      console.log(data);
      return fail(400, data);
    }
  
    const body = {
      requestId: result.data.requestId,
      externalModuleId: result.data.externalModuleId,
      internalModuleId: result.data.internalModuleId,
      annotation: result.data.annotation,
      creditPoints: result.data.creditPoints,
      status: result.data.status,
      createdAt: result.data.createdAt,
      pdfExists: result.data.pdfExists,
      moduleLink: result.data.moduleLink, 
    };
  
    const res = await api.put(`requests/${result.data.requestId}`, body, locals.user?.token);
  
    return { success: true };
  }
  ,
};
