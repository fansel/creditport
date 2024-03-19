import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import { fail, redirect } from '@sveltejs/kit';
import z from 'zod';
import { message, superValidate } from 'sveltekit-superforms';
import { zod } from 'sveltekit-superforms/adapters';
import { change_password_schema } from '$root/lib/schema';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals, cookies }) {
  if (!locals.user) throw redirect(302, `/login`);

  let darkMode = cookies.get('theme') || 'light';
  let useSystemMode = cookies.get('useSystemMode') || false;
  let pageCount = cookies.get('pageCount') || 10;

  return {
    title: 'Einstellungen',
    subtitle: 'Allgemein',
    settings: {
      darkMode: darkMode,
      useSystemMode: useSystemMode,
      pageCount: pageCount
    },
    changePasswordForm: await superValidate(zod(change_password_schema))
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  changePassword: async ({ cookies, request, locals }) => {
    const form = await superValidate(request, zod(change_password_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Ungültiger Input' });
    }

    const res = await api.post(api.routes.user_update_password, { password: form.data.password }, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      console.log(res);
      if (res.http_code == 400) return message(form, { type: 'error', message: 'New password is null or empty!' }, { status: res.http_code });
      if (res.http_code == 403) return message(form, { type: 'error', message: 'Missing authentication!' }, { status: res.http_code });
      if (res.http_code == 404) return message(form, { type: 'error', message: 'User ID not found!' }, { status: res.http_code });

      return message(form, { type: 'success', message: 'Fehler beim ändern des Passworts aufgetreten!' }, { status: res.http_code });
    }

    return message(form, { type: 'success', message: 'Passwort wurde erfolgreich geändert.' });
  }
};
