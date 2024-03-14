import * as api from '$lib/api.js';
import * as config from '$lib/config.js';
import { zfd } from 'zod-form-data';
import { fail, redirect } from '@sveltejs/kit';
import { zod } from 'sveltekit-superforms/adapters';
import { message, setError, superValidate } from 'sveltekit-superforms';
import { add_user_schema, register_user_schema, user_schema } from '$root/lib/schema';
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
    const addUserForm = await superValidate(zod(add_user_schema));

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

    console.log(form);

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Ungültiger Input' }, { status: 400 });
    }

    const res = await api.put(api.routes.user_by_id(form.data.userId), form.data, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      console.log(res);
      if (res.http_code == 400) return message(form, { type: 'error', message: 'Name darf nicht leer sein.' }, { status: res.http_code });
      if (res.http_code == 403) return message(form, { type: 'error', message: 'Du musst Admin sein um einen Nutzer zu bearbeiten.' }, { status: res.http_code });
      if (res.http_code == 404) return message(form, { type: 'error', message: 'Nutzer ID nicht gefunden.' }, { status: res.http_code });
      if (res.http_code == 409) {
        setError(form, 'username', 'Der Nutzername ist bereits vergeben.');
        return message(form, { type: 'error', message: 'Der Nutzername ist bereits vergeben.' }, { status: res.http_code });
      }
      if (res.http_code == 422) return message(form, { type: 'error', message: 'Die Rolle ist Invalid' }, { status: res.http_code });

      return message(form, { type: 'error', message: 'Fehler beim bearbeiten des Nutzers' }, { status: res.http_code });
    }

    return message(form, { type: 'success', message: 'Nutzer wurde erfolgreich bearbeitet' }, { status: 200 });
  },
  addUser: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(add_user_schema));
    console.log(form)

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Ungültiger Input' }, { status: 400 });
    }

    const result = register_user_schema.safeParse(form.data);

    if(!result.success) {
      return message(form, { type: 'error', message: 'Ungültiger Input' }, { status: 400 });
    }

    const res = await api.post(api.routes.register, result.data, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      console.log(res);
      if (res.http_code == 400) return message(form, { type: 'error', message: 'Name oder Passwort darf nicht leer sein.' }, { status: res.http_code });
      if (res.http_code == 403) return message(form, { type: 'error', message: 'Du musst Admin sein um einen Nutzer zu bearbeiten.' }, { status: res.http_code });
      if (res.http_code == 404) return message(form, { type: 'error', message: 'Nutzer ID nicht gefunden.' }, { status: res.http_code });
      if (res.http_code == 409) {
        setError(form, 'username', 'Der Nutzername ist bereits vergeben.');
        return message(form, { type: 'error', message: 'Der Nutzername ist bereits vergeben.' }, { status: res.http_code });
      }
      if (res.http_code == 422) return message(form, { type: 'error', message: 'Die Rolle ist Invalid' }, { status: res.http_code });

      return message(form, { type: 'error', message: 'Fehler beim bearbeiten des Nutzers' }, { status: res.http_code });
    }

    return message(form, { type: 'success', message: 'Nutzer wurde erfolgreich erstellt.' }, { status: 200 });
    // const formData = await request.formData();

    // const schema = zfd.formData({
    //   name: zfd.text(),
    //   password: zfd.text(),
    //   role: zfd.text()
    // });

    // const result = schema.safeParse(formData);

    // if (!result.success) {
    //   const data = {
    //     data: Object.fromEntries(formData),
    //     errors: result.error.flatten().fieldErrors
    //   };
    //   return fail(400, data);
    // }

    // const body = {
    //   username: result.data.name,
    //   password: result.data.password,
    //   role: result.data.role
    // };

    // const res = await api.post(api.routes.register, body, locals.user?.token, { req_type: api.content_type.json, res_type: api.content_type.plain });

    // if (!res.success) {
    //   setFlash({ type: 'error', message: 'Fehler beim erstellen des Nutzers' }, cookies);
    //   return fail(400);
    // }

    // setFlash({ type: 'success', message: 'Nutzer erfolgreich erstellt' }, cookies);
    // return { success: true };
  }
};
