import { redirect, fail } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals, cookies }) {
  if (!locals.user) throw redirect(302, `/login`);

  //Wenn der JWT Token abgelaufen ist, wird der Nutzer automatisch abgemeldet
  if (new Date() > new Date(locals.user.expires_at)) {
    cookies.delete('jwt', { path: '/' });
    locals.user = null;

    throw redirect(300, '/');
  }

  console.log('TEst');

  return {
    user: locals.user && { username: locals.user.username }
  };
}
