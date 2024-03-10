import * as api from '$lib/api.js';
import * as config from '$lib/config.js';
import { zfd } from 'zod-form-data';
import { fail } from '@sveltejs/kit';
import z from 'zod';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals }) {
  let userlist;

  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.role == config.user_roles.ADMIN) {
    //Nutzerliste
    userlist = await api.get('users', locals.user?.token);
  }

  return { title: 'Einstellungen', users: userlist, subtitle: 'Benutzer & Rollen' };
}

/** @type {import('./$types').Actions} */
export const actions = {
  deleteUser: async ({ locals, request }) => {
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
  changeUser: async ({ locals, request }) => {
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
  addUser: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      name: zfd.text(),
      password: zfd.text(),
      role: zfd.text()
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
      username: result.data.name,
      password: result.data.password,
      role: result.data.role
    };

    const res = await api.post(`users/register`, body, locals.user?.token, { req_type: api.content_type.json, res_type: api.content_type.plain });

    return { success: true };
  }
};
