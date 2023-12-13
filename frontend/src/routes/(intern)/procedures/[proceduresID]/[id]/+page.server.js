import * as api from '$lib/api.js';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const { id } = params;
  const modules = await api.get('modules');
  const requests = await api.get(`requests/relatedRequests/${id}`);

  return {
    modules: modules,
    request: requests[0]
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  deleteUni: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      id: zfd.text()
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      return fail(400, { errors: 'keine ID angegeben' });
    }

    const res = await api.del(`universities/${result.data.id}`, locals.user?.token, { res_type: api.content_type.plain });

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
