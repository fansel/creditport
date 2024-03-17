import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';
import { zfd } from 'zod-form-data';
import { z } from 'zod';
import { fail } from '@sveltejs/kit';
import { superValidate, message } from 'sveltekit-superforms/server';
import { add_external_module, add_university } from '$root/lib/schema';
import { zod } from 'sveltekit-superforms/adapters';
import { status_requests } from '$root/lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
    const universities = await api.get(api.routes.university_all);
    if(!universities.success){
      throw error(404, { message: 'Fehler beim Laden der Universitäten' });
    }
    const courses = await api.get(api.routes.course_all);
    if (!courses.success){
      throw error(404, { message: 'Fehler beim Laden der Studiengänge' });
    }
    return {
      universities: universities.data,
      testUni: universities.data[0],
      courses: courses.data,
      form: await superValidate(zod(add_external_module)),
      uniForm: await superValidate(zod(add_university)),
      title: 'Vorgang erstellen',
    };
  }

  /** @type {import('./$types').Actions} */
export const actions = {
    addExternalModule: async ({ locals, request, cookies }) => {
      const form = await superValidate(request, zod(add_external_module));
  
      if (!form.valid) {
        return message(form, { type: 'error', message: 'Dein Input ist ungültig' }, cookies);
      }
      console.log("validation successful :)")
  
      const res = await api.post(api.routes.module_all_external, form.data, locals.user?.token);
  
      if (!res.success) {
        console.log("res: ", res)
        return message(form, { type: 'error', message: 'Fehler beim Erstellen' }, cookies);
      }
  
      return message(form, { type: 'success', message: 'Wurde erfolgreich gespeichert' }, cookies);
    },
    addUni: async ({ locals, request, cookies }) => {
      const uniForm = await superValidate(request, zod(add_university));
  
      if (!uniForm.valid) {
        return message(form, { type: 'error', message: 'Dein Input ist ungültig' }, cookies);
      }
      console.log("validation successful :)")
  
      const res = await api.post(api.routes.university_all, uniForm.data, locals.user?.token);
  
      if (!res.success) {
        console.log("res: ", res)
        return message(uniForm, { type: 'error', message: 'Fehler beim Erstellen' }, cookies);
      }
  
      return message(uniForm, { type: 'success', message: 'Universität erfolgreich hinzugefügt' }, cookies);
    }
  };