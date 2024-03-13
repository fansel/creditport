import * as config from '$lib/config';
import * as api from '$lib/api';
import { redirect, error, fail } from '@sveltejs/kit';
import { superValidate, setError, message } from 'sveltekit-superforms';
import { zod } from 'sveltekit-superforms/adapters';
import { add_course_schema, update_course_schema, update_internal_modul_schema } from '$root/lib/schema';
import { zfd } from 'zod-form-data';
import { setFlash } from 'sveltekit-flash-message/server';

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

    const updateModuleForm = superValidate(zod(update_internal_modul_schema));
    // const importUniForm = superValidate(zod(universities_upload_schema));

    return {
      title: 'Einstellungen',
      subtitle: 'Studiengänge',
      courses: courses.data,
      modules: modules.data,
      updateCourseForm,
      addCourseForm,
      updateModuleForm
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
      return fail(400, form);
    }

    const res = await api.post(api.routes.course_all, form.data, locals.user?.token);

    console.log(res);

    if (!res.success) {
      setError(form, 'courseName', 'Leider konnte der Studiengang nicht erstellt werden.');
      return message(form, { type: 'error', message: 'Fehler beim erstellen des Studiengangs' }, cookies);
    }

    return message(form, { type: 'success', message: 'Erfolgreich erstellt.' }, cookies);
  },
  deleteCourse: async ({ locals, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      id: zfd.text()
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      return fail(400, { errors: 'keine ID angegeben' });
    }

    const res = await api.del(api.routes.course_by_id(result.data.id), locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      setFlash({ type: 'error', message: 'Fehler beim löschen des Studiengangs' }, cookies);
      return fail(400, { errors: 'Fehler beim löschen des Studiengangs' });
    }

    return { success: true };
  },
  updateCourse: async ({ locals, request }) => {
    const form = await superValidate(request, zod(update_course_schema));

    if (!form.valid) {
      return fail(400, form);
    }

    const res = await api.put(api.routes.course_by_id(form.data.courseId), form.data, locals.user?.token);

    if (!res.success) {
      setError(form, 'courseName', 'Leider konnte der Studiengang nicht bearbeitet werden.');
      return message(form, { type: 'error', message: 'Fehler beim bearbeiten des Studiengangs' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Studiengang wurde erfolgreich bearbeitet' }, { status: 200 });
  },
  updateModule: async ({ locals, request }) => {
    const form = await superValidate(request, zod(update_internal_modul_schema));

    if (!form.valid) {
      return fail(400, form);
    }

    const res = await api.put(api.routes.module_internal_by_id(form.data.moduleId), form.data, locals.user?.token);

    if (!res.success) {
      setError(form, 'moduleName', 'Leider konnte das Module nicht bearbeitet werden.');
      return message(form, { type: 'error', message: 'Fehler beim bearbeiten des Moduls' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Modul wurde erfolgreich bearbeitet' }, { status: 200 });
  },
  deleteModule: async ({ locals, request, cookies }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      id: zfd.text()
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      return fail(400, { errors: 'keine ID angegeben' });
    }

    const res = await api.del(api.routes.module_internal_by_id(result.data.id), locals.user?.token, { res_type: api.content_type.plain });

    console.log(res)

    if (!res.success) {
      setFlash({ type: 'error', message: 'Fehler beim löschen des Moduls' }, cookies);
      return fail(400, { errors: 'Fehler beim löschen des Moduls' });
    }

    return { success: true };
  }
};
