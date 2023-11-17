import { redirect, fail } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals }) {
  if (!locals.user) throw redirect(302, `/login`);

  return {
    user: locals.user && { username: locals.user.username }
  };
}
