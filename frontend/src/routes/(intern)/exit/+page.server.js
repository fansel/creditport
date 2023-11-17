import * as api from '$lib/api.js';
import { redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ cookies, locals }) {
  cookies.delete('jwt', { path: '/' });
  locals.user = null;

  throw redirect(300, '/');
}
