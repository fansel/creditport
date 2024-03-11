import * as config from '$lib/config';
import * as api from '$lib/api';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, locals }) {
  const res = await api.get(api.routes.procedure_all, locals.user?.token);

  if (!res.success) {
    throw error(res.http_code, { message: 'Fehler beim Laden der Vorgänge' });
  }

  return { procedures: res.data, title: 'Vorgänge' };
}

/** @type {import('./$types').Actions} */
export const actions = {
  editProcedure: async ({ locals, request }) => {
    const formData = await request.formData();

    // const schema = zfd.formData({
    //   id: zfd.text()
    // });

    // const result = schema.safeParse(formData);

    // if (!result.success) {
    //   return fail(400, { errors: 'keine ID angegeben' });
    // }

    // const res = await api.del(`universities/${result.data.id}`, locals.user?.token, { res_type: api.content_type.plain });

    return { success: true };
  },
  changeUni: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      id: zfd.text(),
      name: zfd.text(z.string({ required_error: 'Name darf nicht leer sein' }))
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
      uniName: result.data.name
    };

    const res = await api.put(`universities/${result.data.id}`, body, locals.user?.token);

    return { success: true };
  },
  addUni: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      name: zfd.text()
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
      uniName: result.data.name
    };

    const res = await api.post(`universities`, body, locals.user?.token);

    return { success: true };
  }
};

export const ssr = false;
