import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import { fail } from '@sveltejs/kit';
import z from 'zod';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals, cookies }) {
  let unilist;

  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.username == 'admin') {
    //Nutzerliste
    unilist = await api.get('universities', null, api.content_type.json);
  }

  return {
    title: 'Dashboard',
    subtitle: 'Universitäten',
    universities: unilist
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

    const res = await api.del(`universities/${result.data.id}`, locals.user?.token, api.content_type.plain);

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

    const res = await api.put(`universities/${result.data.id}`, body, locals.user?.token, api.content_type.json);

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

    const res = await api.post(`universities`, body, locals.user?.token, api.content_type.json);

    return { success: true };
  }
};
