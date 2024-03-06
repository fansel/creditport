import * as api from '$lib/api.js';
import { error } from '@sveltejs/kit';
import { zfd } from 'zod-form-data';
import { z } from 'zod';
import { fail } from '@sveltejs/kit';

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

  const modules = await api.get('modules/internal');
  const request = await api.get(`requests/relatedRequests/${id}`);
  // const request = await api.get(`requests/${uuid}`)

  if (!request || request == 500 || request == 404) {
    throw error(404, { message: 'Antrag existiert nicht' });
  }

  return {
    modules: modules,
    request: request,
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
