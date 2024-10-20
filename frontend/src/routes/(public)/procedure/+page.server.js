import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';
import { zfd } from 'zod-form-data';
import { z } from 'zod';
import { fail } from '@sveltejs/kit';
import { superValidate, message } from 'sveltekit-superforms/server';
import { add_external_module, add_university, default_request, modulantraege as lastStep, modulantraege_senden } from '$root/lib/schema';
import { zod } from 'sveltekit-superforms/adapters';
import { status_requests } from '$root/lib/config';
import { redirect } from 'sveltekit-flash-message/server';
import { randomUUID } from '$lib/util';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const universities = await api.get(api.routes.university_all);
  if (!universities.success) {
    throw error(404, { message: 'Fehler beim Laden der Universitäten' });
  }
  const courses = await api.get(api.routes.course_all);
  if (!courses.success) {
    throw error(404, { message: 'Fehler beim Laden der Studiengänge' });
  }

  const external_modules = await api.get(api.routes.module_all_external);
  if (!external_modules.success) {
    throw error(404, { message: 'Fehler beim Laden der externen Module' });
  }

  const internal_modules = await api.get(api.routes.module_all_internal);
  if (!internal_modules.success) {
    throw error(404, { message: 'Fehler beim Laden der externen Module' });
  }

  console.log('Server Load ausgeführt');

  const default_procedure = {
    // annotation: 'hallo',
    // universityId: '698152a1-8637-480e-a4be-696e8c1fc90a',
    // courseId: '6298a54f-e1b1-41f6-a4d9-331e4265fc69',
    requests: [default_request(randomUUID())]
  };

  return {
    universities: universities.data.filter((u) => u.verified == true),
    courses: courses.data,
    external_modules: external_modules.data.filter((c) => c.verified == true),
    internal_modules: internal_modules.data,
    externalModuleForm: await superValidate(zod(add_external_module)),
    uniForm: await superValidate(zod(add_university)),
    multiForm: await superValidate(zod(lastStep), { errors: false }),
    title: 'Vorgang erstellen'
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  addExternalModule: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(add_external_module));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Dein Input ist ungültig' }, { status: 400 });
    }

    const res = await api.post(api.routes.module_all_external, form.data, null);

    if (!res.success) {
      console.log('res: ', res);
      return message(form, { type: 'error', message: 'Fehler beim Erstellen des Moduls' }, { status: 400 });
    }

    return message(form, { selectedModule: res.data, type: 'success', message: 'Modul wurde erfolgreich hinzugefügt' }, { status: 200 });
  },
  addUni: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(add_university));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Dein Input ist ungültig' }, { status: 400 });
    }

    const res = await api.post(api.routes.university_all, form.data, null);

    if (!res.success) {
      console.log('res: ', res);
      return message(form, { type: 'error', message: 'Fehler beim Erstellen der Universität' }, { status: 400 });
    }

    return message(form, { selectedUni: res.data, type: 'success', message: 'Universität erfolgreich hinzugefügt' }, { status: 200 });
    // return { form, selectedUni: res.data };
  },
  multiForm: async ({ locals, request, cookies }) => {
    const multiForm = await superValidate(request, zod(lastStep));

    console.log('Submit on Server');

    if (!multiForm.valid) {
      return message(multiForm, { type: 'error', message: 'Dein Input ist ungültig' }, { status: 400 });
    }
    console.log('Validation Procedure Successfully');

    const body = {
      annotation: multiForm.data.annotation,
      universityId: multiForm.data.universityId,
      courseId: multiForm.data.courseId,
      requests: multiForm.data.requests.map((r) => ({
        annotationStudent: r.annotationStudent,
        annotationCommittee: r.annotationCommittee,
        externalModuleId: r.externalModuleId,
        internalModuleId: r.internalModuleId,
        moduleLink: r.moduleLink
      }))
    };

    const res = await api.post(api.routes.procedure_all, body, null);

    if (!res.success) {
      console.log(res);
      return message(multiForm, { type: 'error', message: 'Fehler beim Erstellen des Vorgangs' }, { status: 400 });
    }

    const procedure_id = res.data.procedureId;

    for (let i = 0; i < res.data.requests.length; i++) {
      const requestId = res.data.requests[i].requestId;
      const form_data = new FormData();
      form_data.append('file', multiForm.data.requests[i].file);

      const pdf_res = await api.post(api.routes.pdf_upload(requestId), form_data, null, { req_type: api.content_type.file, res_type: api.content_type.plain });
      if (!pdf_res.success) {
        return message(multiForm, { type: 'error', message: 'Fehler beim Hochladen der PDF' }, { status: 400 });
      }
    }

    if (!procedure_id) {
      return message(multiForm, { type: 'error', message: 'Fehler beim Erstellen des Vorgangs' }, { status: 400 });
    }

    throw redirect(307, `/status/${procedure_id}`, { type: 'success', message: 'Vorgang erfolgreich erstellt.' }, cookies);
  }
};
