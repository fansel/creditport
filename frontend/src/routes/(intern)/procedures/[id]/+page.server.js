import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';
import { zfd } from 'zod-form-data';
import { z } from 'zod';
import { fail } from '@sveltejs/kit';
import { superValidate, message } from 'sveltekit-superforms/server';
import { full_request_schema, update_external_module } from '$root/lib/schema';
import { zod } from 'sveltekit-superforms/adapters';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, locals }) {
  const { id } = params;

  if (!locals.user) throw redirect(302, `/login`);

  //Wenn der JWT Token abgelaufen ist, wird der Nutzer automatisch abgemeldet
  if (new Date() > new Date(locals.user.expires_at)) {
    cookies.delete('jwt', { path: '/' });
    locals.user = null;

    throw redirect(300, '/');
  }

  const modules = await api.get(api.routes.module_all_internal);
  const request = await api.get(api.routes.request_by_id_related(id));
  const procedure = await api.get(api.routes.procedure_by_id(request.data.procedureId));

  if (!modules.success) {
    console.log(modules.data);
    throw error(500, { message: 'Fehler beim laden der Module' });
  }

  if (!request.success && request.http_code == 404) {
    throw error(404, { message: 'Antrag existiert nicht' });
  }

  return {
    modules: modules.data,
    request: request.data,
    procedure: procedure.data,
    user: locals.user,
    updateRequestForm: await superValidate(request.data, zod(full_request_schema)),
    updateModuleForm: await superValidate(zod(update_external_module)),
    title: 'Antrag bearbeiten'
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  updateRequest: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(full_request_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Dein Input ist ungültig' }, cookies);
    }

    const body = {
      requestId: form.data.requestId,
      externalModuleIds: form.data.externalModules.map((r) => r.moduleId),
      internalModuleIds: form.data.internalModules.map((r) => r.moduleId),
      annotationStudent: form.data.annotationStudent,
      annotationCommittee: form.data.annotationCommittee,
      statusRequest: form.data.statusRequest,
      createdAt: form.data.createdAt,
      pdfExists: form.data.pdfExists,
      moduleLink: form.data.moduleLink,
      favored: false
    };

    const res = await api.put(api.routes.request_by_id(form.data.requestId), body, locals.user?.token);

    if (!res.success) {
      return message(form, { type: 'error', message: 'Fehler beim speichern des Antrags' }, cookies);
    }

    return message(form, { type: 'success', message: 'Wurde erfolgreich gespeichert' }, cookies);
  },
  updateExternalModule: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(update_external_module));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Dein Input ist ungültig' }, cookies);
    }

    // const body = {
    //   requestId: form.data.requestId,
    //   externalModuleIds: form.data.externalModules.map((r) => r.moduleId),
    //   internalModuleIds: form.data.internalModules.map((r) => r.moduleId),
    //   annotationStudent: form.data.annotationStudent,
    //   annotationCommittee: form.data.annotationCommittee,
    //   statusRequest: form.data.statusRequest,
    //   createdAt: form.data.createdAt,
    //   pdfExists: form.data.pdfExists,
    //   moduleLink: form.data.moduleLink,
    //   favored: false
    // };

    const res = await api.put(api.routes.module_external_by_id(form.data.moduleId), form.data, locals.user?.token);

    if (!res.success) {
      return message(form, { type: 'error', message: 'Fehler beim speichern des Moduls' }, cookies);
    }

    return message(form, { type: 'success', message: 'Wurde erfolgreich gespeichert' }, cookies);
  }
};
