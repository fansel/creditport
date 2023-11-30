import * as config from '$lib/config.js';
import { redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ cookies, locals }) {
  cookies.delete('jwt', { secure: config.secure_connection, path: '/' });
  locals.user = null;

  throw redirect(300, '/');
}
