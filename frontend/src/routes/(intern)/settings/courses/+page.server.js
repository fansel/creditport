import * as config from '$lib/config';
import * as api from '$lib/api';
import { redirect, error, fail } from '@sveltejs/kit';
import { superValidate } from 'sveltekit-superforms';
import { zod } from 'sveltekit-superforms/adapters';
import { add_course_schema, update_course_schema } from '$root/lib/schema';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals }) {
  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.role == config.user_roles.ADMIN) {
    //Nutzerliste
    const courses = await api.get(api.routes.course_all);
    const modules = await api.get(api.routes.module_all_internal);

    if (!courses.success) {
      throw error(courses.status, { message: 'Fehler beim Laden der Studiengänge' });
    }

    if (!modules.success) {
      throw error(courses.status, { message: 'Fehler beim Laden der Internen Module' });
    }

    const updateCourseForm = superValidate(zod(update_course_schema));
    const addCourseForm = superValidate(zod(add_course_schema));
    // const importUniForm = superValidate(zod(universities_upload_schema));

    return {
      title: 'Einstellungen',
      subtitle: 'Studiengänge',
      courses: courses.data,
      modules: modules.data,
      updateCourseForm,
      addCourseForm
      // updateUniForm,
      // importUniForm
    };
  }

  throw redirect(303, '/settings');
}

/** @type {import('./$types').Actions} */
export const actions = {
  addCourse: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(add_course_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Bitte wähle eine Datei aus' }, { status: 400 });
    }

    
  },
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
