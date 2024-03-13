import * as config from '$lib/config';
import * as api from '$lib/api';
import { error } from '@sveltejs/kit';
import { superValidate, message } from 'sveltekit-superforms';
import { zod } from 'sveltekit-superforms/adapters';
import { procedure_schema } from '$root/lib/schema';

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

    console.log(form.data)


    const body = {
      procedureId: form.data.procedureId,
      annotation: form.data.annotation,
      university: form.data.university,
      course: form.data.course,
      createdAt: form.data.createdAt,
      requests: form.data.requestDetails.map((r) => ({
        requestId: r.requestId,
        annotationStudent: r.annotationStudent,
        annotationCommittee: r.annotationCommittee,
        favored: false,
        moduleLink: r.moduleLink,
        pdfExists: r.pdfExists,
        createdAt: r.createdAt,
        internalModuleIds: r.internalModules.map((r) => r.moduleId),
        externalModuleIds: r.externalModules.map((r) => r.moduleId),
        statusRequest: r.statusRequest
      }))
    };

    console.log(body)

    const res = await api.put(api.routes.procedure_by_id(form.data.procedureId), body, locals.user?.token);

    console.log(res)

    if (!res.success) {
      return message(form, { type: 'error', message: 'Fehler beim Speichern des Vorgangs.' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Erfolgreich gespeichert.' });
  }
};
