import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';

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
  putRequest: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      id: zfd.text(),
      annotation: zfd.text(),
      status: zfd.text(z.string({ required_error: 'Status darf nicht leer sein' }))
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      const data = {
        data: Object.fromEntries(formData),
        errors: result.error.flatten().fieldErrors
      };
      return fail(400, data);
    }

    const body = {
    //   "requestId": 1,
    // "externalModuleId": "10-201-2001-2",
    // "internalModuleId": "testId",
    // "annotation": "Algorithmen und Datenstrukturen",
    // "creditPoints": 5,
    // "status": "NICHT_BEARBEITET",
    // "createdAt": "2024-01-26T10:06:15.343687Z",
    // "pdfExists": true,
    // "moduleLink": null
      
      annotation: result.data.annotation,
      status: result.data.status
      // annotation: result.data.annotation
      // annotation: result.data.annotation
    };

    const res = await api.put(`requests/${id}`, body, locals.user?.token);

    return { success: true };
  },
};
