/** @type {import('@sveltejs/kit').Handle} */

/** @type {import('@sveltejs/kit').Handle} */
export async function handle({ event, resolve }) {
  const jwt = event.cookies.get('jwt');

  event.locals.user = jwt ? JSON.parse(atob(jwt)) : null;

  return resolve(event);
}