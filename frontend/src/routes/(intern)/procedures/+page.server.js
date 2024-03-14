import * as config from '$lib/config';
import * as api from '$lib/api';
import { error, fail } from '@sveltejs/kit';
import { superValidate, message } from 'sveltekit-superforms';
import { zod } from 'sveltekit-superforms/adapters';
import { procedure_schema, procedure_by_id_schema } from '$root/lib/schema';
import { zfd } from 'zod-form-data';
import { setFlash } from 'sveltekit-flash-message/server';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, locals }) {
  const res = await api.get(api.routes.procedure_all, locals.user?.token);

  if (!res.success) {
    throw error(res.http_code, { message: 'Fehler beim Laden der Vorgänge' });
  }

  return { procedures: res.data, title: 'Vorgänge', updateProcedureForm: await superValidate(zod(procedure_schema)) };
}

/** @type {import('./$types').Actions} */
export const actions = {
  updateProcedure: async ({ locals, request }) => {
    const form = await superValidate(request, zod(procedure_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Fehlerhafter Input!' }, { status: 400 });
    }

    const body = { annotation: form.data.annotation };

    const res = await api.put(api.routes.procedure_by_id(form.data.procedureId), body, locals.user?.token);

    console.log(res);

    if (!res.success) {
      return message(form, { type: 'error', message: 'Fehler beim Speichern des Vorgangs.' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Erfolgreich gespeichert.' });
  },
  archiveProcedure: async ({ locals, request, cookies }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      procedureId: zfd.text()
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      setFlash({ type: 'error', message: 'Keine ID angegeben!' }, cookies);
      return fail(400, { errors: 'keine ID angegeben' });
    }

    const res = await api.post(api.routes.procedure_archive(result.data.procedureId), {}, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      console.log(res);
      if (res.http_code == 428) {
        setFlash({ type: 'error', message: 'Vorgang nicht vollständig, kann nicht archiviert werden!' }, cookies);
        return fail(res.http_code, { errors: 'Vorgang nicht vollständig, kann nicht archiviert werden!' });
      }
      setFlash({ type: 'error', message: 'Fehler beim Archivieren des Vorgangs.' }, cookies);

      return fail(400, { errors: 'Fehler' });
    }

    setFlash({ type: 'success', message: 'Erfolgreich archiviert.' }, cookies);
    return { success: true };
  }
};
