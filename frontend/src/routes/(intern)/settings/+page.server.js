import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import { fail } from '@sveltejs/kit';
import z from 'zod';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals, cookies }) {
  let userlist;
  let unilist;

  let darkMode = cookies.get('theme') || 'light';
  let useSystemMode = cookies.get('useSystemMode') || false;
  let pageCount = cookies.get('pageCount') || 10;

  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.username == 'admin') {
    //Nutzerliste
    userlist = await api.get('api/usermanagement/users', locals.user?.token, api.content_type.json);
    unilist = await api.get('api/universities', null, api.content_type.json);
  }

  return {
    title: 'Dashboard',
    users: userlist,
    universities: unilist,
    settings: {
      darkMode: darkMode,
      useSystemMode: useSystemMode,
      pageCount: pageCount
    }
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  changePassword: async ({ cookies, request }) => {
    const data = await request.formData();

    const schema = zfd.formData({
      aktuellesPassword: zfd.text(z.string().optional()),
      neuesPassword: zfd.text(z.string().optional())
    });

    const result = schema.safeParse(data);

    if (!result.success) {
      const data = {
        data: Object.fromEntries(formData),
        errors: result.error.flatten().fieldErrors
      };
      return fail(400, data);
    }

    return { success: true };
  }
};
