import * as api from '$lib/api.js';
import * as config from '$lib/config.js';
import { zfd } from 'zod-form-data';
import { fail, redirect } from '@sveltejs/kit';
import { zod } from 'sveltekit-superforms/adapters';
import { message, superValidate } from 'sveltekit-superforms';
import { user_schema } from '$root/lib/schema';
import { setFlash } from 'sveltekit-flash-message/server';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals }) {
  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.role == config.user_roles.ADMIN) {
    //Nutzerliste
    const res = await api.get(api.routes.user_all, locals.user?.token);

    if (!res.success) {
      throw error(res.http_code, { message: 'Fehler beim Laden der Nutzer' });
    }

    const updateUserForm = await superValidate(zod(user_schema));
    const addUserForm = await superValidate(zod(user_schema));

    return { title: 'Einstellungen', users: res.data, subtitle: 'Benutzer & Rollen', updateUserForm, addUserForm };
  }

  throw redirect(303, '/settings');
}

/** @type {import('./$types').Actions} */
export const actions = {
  deleteUser: async ({ locals, request, cookies }) => {
    const form = await request.formData();

    const userId = form.get('userId');

    if (!userId) {
      setFlash({ type: 'error', message: 'Keine Nutzer ID angegeben' }, cookies);
      return fail(400);
    }

    const res = await api.del(api.routes.user_by_id(userId), locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      setFlash({ type: 'error', message: 'Fehler beim löschen des Nutzers' }, cookies);
      return fail(res.http_code);
    }

    setFlash({ type: 'success', message: 'Nutzer wurde erfolgreich gelöscht' }, cookies);
    return { success: true };
  },
  updateUser: async ({ locals, request }) => {
    const form = await superValidate(request, zod(user_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Ungültiger Input' }, { status: 400 });
    }

    const res = await api.put(api.routes.user_update, form.data, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      return message(form, { type: 'error', message: 'Fehler beim bearbeiten des Nutzers' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Nutzer wurde erfolgreich bearbeitet' }, { status: 200 });
  },
  addUser: async ({ locals, request, cookies }) => {
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

    const res = await api.post(api.routes.register, body, locals.user?.token, { req_type: api.content_type.json, res_type: api.content_type.plain });

    if (!res.success) {
      setFlash({ type: 'error', message: 'Fehler beim erstellen des Nutzers' }, cookies);
      return fail(400);
    }

    setFlash({ type: 'success', message: 'Nutzer erfolgreich erstellt' }, cookies);
    return { success: true };
  }
};
