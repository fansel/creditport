import * as config from '$lib/config';
import * as api from '$lib/api';
import { redirect, error, fail } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals }) {
  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.role == config.user_roles.ADMIN) {
    //Nutzerliste
    const res = await api.get(api.routes.course_all);

    if (!res.success) {
      throw error(res.status, { message: 'Fehler beim Laden der Studiengänge' });
    }

    // const updateUniForm = superValidate(zod(universities_schema));
    // const importUniForm = superValidate(zod(universities_upload_schema));

    return {
      title: 'Einstellungen',
      subtitle: 'Studiengänge',
      courses: res.data
      // updateUniForm,
      // importUniForm
    };
  }

  throw redirect(303, '/settings');
}
