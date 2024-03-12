import * as api from '$lib/api.js';
import * as config from '$lib/config.js';
import { zfd } from 'zod-form-data';
import { fail, redirect } from '@sveltejs/kit';
import z from 'zod';
import { universities_schema } from '$root/lib/schema';
import { zod } from 'sveltekit-superforms/adapters';
import { message, superValidate } from 'sveltekit-superforms';
import { setFlash } from 'sveltekit-flash-message/server';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals, cookies }) {
  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.role == config.user_roles.ADMIN) {
    //Nutzerliste
    const res = await api.get(api.routes.university_all);

    if (!res.success) {
      throw error(res.status, { message: 'Fehler beim Laden der Universitäten' });
    }

    const updateUniForm = superValidate(zod(universities_schema));

    return {
      title: 'Dashboard',
      subtitle: 'Universitäten',
      universities: res.data,
      updateUniForm
    };
  }

  throw redirect(303, '/settings');
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

    const res = await api.del(api.routes.university_by_id(result.data.id), locals.user?.token, { res_type: api.content_type.plain });

    return { success: true };
  },
  updateUni: async ({ locals, request }) => {
    const form = await superValidate(request, zod(universities_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Ungültiger Input' }, { status: 400 });
    }

    const res = await api.put(api.routes.university_by_id(form.data.uniId), form.data, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      return message(form, { type: 'error', message: 'Fehler beim bearbeiten der Universität' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Universität wurde erfolgreich bearbeitet' }, { status: 200 });
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

    const res = await api.post(api.routes.university_all, body, locals.user?.token);

    return { success: true };
  }
};
