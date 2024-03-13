import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';
import { zfd } from 'zod-form-data';
import { z } from 'zod';
import { fail } from '@sveltejs/kit';
import { superValidate } from 'sveltekit-superforms/server'

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
  let procedureId = request.data.procedureId;
  console.log(procedureId);
  const procedure = await api.get(api.routes.procedure_by_id(procedureId));
  // const request = await api.get(`requests/${uuid}`)

  if (!modules.success) {
    console.log(modules.data)
    throw error(500, {message: 'Fehler beim laden der Module'})
  }

  if(!request.success && request.http_code == 404) {
    throw error(404, {message: 'Antrag existiert nicht'})
  }


  return {
    modules: modules.data,
    request: request.data,
    procedure: procedure.data,
    user: locals.user,
    title: 'Antrag bearbeiten'
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  updateRequest: async ({ locals, request }) => {
    const formData = await request.json();
    console.log(formData);

    return { success: true };
  }
};
