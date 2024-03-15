import * as config from '$lib/config';
import * as api from '$lib/api';
import { redirect, error, fail } from '@sveltejs/kit';
import { message, setError, superValidate, withFiles } from 'sveltekit-superforms';
import { zod } from 'sveltekit-superforms/adapters';
import { add_course_schema, add_internal_modul_schema, courses_import_schema, courses_upload_schema, update_course_schema, update_internal_modul_schema } from '$root/lib/schema';
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
      throw error(courses.http_code, { message: 'Fehler beim Laden der Studiengänge' });
    }

    if (!modules.success) {
      throw error(courses.http_code, { message: 'Fehler beim Laden der Internen Module' });
    }

    const updateCourseForm = superValidate(zod(update_course_schema));
    const addCourseForm = superValidate(zod(add_course_schema));

    const updateModuleForm = superValidate(zod(update_internal_modul_schema));
    const importCourseForm = superValidate(zod(courses_upload_schema));
    const importModuleForm = superValidate(zod(courses_upload_schema));
    const addModuleForm = superValidate(zod(add_internal_modul_schema));

    return {
      title: 'Einstellungen',
      subtitle: 'Studiengänge',
      courses: courses.data,
      modules: modules.data,
      updateCourseForm,
      addCourseForm,
      updateModuleForm,
      addModuleForm,
      importCourseForm,
      importModuleForm
      // updateUniForm,
      // importUniForm
    };
  }

  throw redirect(303, '/settings');
}

/** @type {import('./$types').Actions} */
export const actions = {
  importCourse: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(courses_upload_schema));

    if (!form.valid) {
      return message(withFiles({ form }), { type: 'error', message: 'Bitte wähle eine Datei aus' }, { status: 400 });
    }

    try {
      const parsedText = await form.data.file.text();
      const parsedJsonObj = JSON.parse(parsedText);

      // Überprüfe ob das importierte Objekt die korrekte Form hat
      const result = courses_import_schema.safeParse(parsedJsonObj);

      if (!result.success) {
        setError(form, 'file', 'Nicht das richtige JSON Format');
        return message(form, { type: 'error', message: 'Fehler beim importieren der Universitäten' }, { status: 400 });
      }

      for (let index = 0; index < result.data.length; index++) {
        const course = result.data[index];

        // Lege erst den Studiengang an
        const resCourse = await api.post(api.routes.course_all, { courseName: course.courseName }, locals.user?.token, { res_type: api.content_type.json });

        if (!resCourse.success) {
          return message(form, { type: 'error', message: 'Fehler beim importieren der Studiengänge' }, { status: 400 });
        }

        for (let z = 0; z < course.modules.length; z++) {
          const module = course.modules[z];

          const body = course.modules.map((m) => ({
            number: m.number,
            moduleName: m.moduleName,
            moduleDescription: m.moduleDescription,
            creditPoints: m.creditPoints,
            courses: [
              {
                courseId: resCourse.data.courseId,
                courseName: resCourse.data.courseName
              }
            ]
          }));

          const res = await api.post(api.routes.module_internal_import, body, locals.user?.token, { res_type: api.content_type.plain });

          if (!res.success) {
            console.log(res);
            return message(form, { type: 'error', message: 'Fehler beim anlegen eines Moduls' }, { status: 400 });
          }
        }
      }

      return message(form, { type: 'success', message: 'Studiengänge wurden erfolgreich importiert.' }, { status: 200 });
    } catch (error) {
      console.error(error);
      setError(form, 'file', 'Fehler beim parsen der JSON Datei');
      return fail(400, withFiles({ form }));
    }
  },
  importModules: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(courses_upload_schema));

    if (!form.valid) {
      return message(withFiles({ form }), { type: 'error', message: 'Bitte wähle eine Datei aus' }, { status: 400 });
    }

    return { form };

    // try {
    //   const parsedText = await form.data.file.text();
    //   const parsedJsonObj = JSON.parse(parsedText);

    //   // Überprüfe ob das importierte Objekt die korrekte Form hat
    //   const result = courses_import_schema.safeParse(parsedJsonObj);

    //   if (!result.success) {
    //     setError(form, 'file', 'Nicht das richtige JSON Format');
    //     return message(form, { type: 'error', message: 'Fehler beim importieren der Universitäten' }, { status: 400 });
    //   }

    //   for (let index = 0; index < result.data.length; index++) {
    //     const course = result.data[index];

    //     // Lege erst den Studiengang an
    //     const resCourse = await api.post(api.routes.course_all, { courseName: course.courseName }, locals.user?.token, { res_type: api.content_type.json });

    //     if (!resCourse.success) {
    //       return message(form, { type: 'error', message: 'Fehler beim importieren der Studiengänge' }, { status: 400 });
    //     }

    //     for (let z = 0; z < course.modules.length; z++) {
    //       const module = course.modules[z];

    //       const body = course.modules.map((m) => ({
    //         number: m.number,
    //         moduleName: m.moduleName,
    //         moduleDescription: m.moduleDescription,
    //         creditPoints: m.creditPoints,
    //         courses: [
    //           {
    //             courseId: resCourse.data.courseId,
    //             courseName: resCourse.data.courseName
    //           }
    //         ]
    //       }));

    //       const res = await api.post(api.routes.module_internal_import, body, locals.user?.token, { res_type: api.content_type.plain });

    //       if (!res.success) {
    //         console.log(res);
    //         return message(form, { type: 'error', message: 'Fehler beim anlegen eines Moduls' }, { status: 400 });
    //       }
    //     }
    //   }

    //   return message(form, { type: 'success', message: 'Studiengänge wurden erfolgreich importiert.' }, { status: 200 });
    // } catch (error) {
    //   console.error(error);
    //   setError(form, 'file', 'Fehler beim parsen der JSON Datei');
    //   return fail(400, withFiles({ form }));
    // }
  },
  addCourse: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(add_course_schema));

    if (!form.valid) {
      return message({ type: 'error', message: 'Fehler beim anlegen des Moduls' });
    }

    const res = await api.post(api.routes.course_all, form.data, locals.user?.token);

    if (!res.success) {
      console.log(res);
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
      console.log(res);

      setFlash({ type: 'error', message: 'Fehler beim löschen des Studiengangs' }, cookies);
      return fail(400, { errors: 'Fehler beim löschen des Studiengangs' });
    }

    return { success: true };
  },
  updateCourse: async ({ locals, request }) => {
    const form = await superValidate(request, zod(update_course_schema));

    if (!form.valid) {
      return fail(400, { form });
    }

    const res = await api.put(api.routes.course_by_id(form.data.courseId), form.data, locals.user?.token);

    if (!res.success) {
      console.log(res);

      setError(form, 'courseName', 'Leider konnte der Studiengang nicht bearbeitet werden.');
      return message(form, { type: 'error', message: 'Fehler beim bearbeiten des Studiengangs' }, { status: 400 });
    }

    return message(form, { type: 'success', message: 'Studiengang wurde erfolgreich bearbeitet' }, { status: 200 });
  },
  updateModule: async ({ locals, request }) => {
    const form = await superValidate(request, zod(update_internal_modul_schema));

    if (!form.valid) {
      return fail(400, { form });
    }

    const res = await api.put(api.routes.module_internal_by_id(form.data.id), form.data, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      console.log(res);

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

    if (!res.success) {
      console.log(res);

      setFlash({ type: 'error', message: 'Fehler beim löschen des Moduls' }, cookies);
      return fail(400, { errors: 'Fehler beim löschen des Moduls' });
    }

    return { success: true };
  },
  addModule: async ({ locals, request, cookies }) => {
    const form = await superValidate(request, zod(add_internal_modul_schema));

    if (!form.valid) {
      return fail(400, { form });
    }

    const res = await api.post(api.routes.module_all_internal, form.data, locals.user?.token, { res_type: api.content_type.plain });

    if (!res.success) {
      console.log(res);
      setError(form, 'moduleName', 'Leider konnte das Module nicht erstellt werden.');
      return message(form, { type: 'error', message: 'Fehler beim erstellen des Moduls' }, cookies);
    }

    return message(form, { type: 'success', message: 'Erfolgreich erstellt.' }, cookies);
  }
};
