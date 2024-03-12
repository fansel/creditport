import { PUBLIC_ENV } from '$env/dynamic/public';

/** @type {import('@sveltejs/kit').Handle} */
export async function handle({ event, resolve }) {
  const jwt = event.cookies.get('jwt');
  const theme = event.cookies.get('theme');

  event.locals.user = jwt ? JSON.parse(atob(jwt)) : null;

  if (theme) {
    return await resolve(event, {
      transformPageChunk: ({ html }) => {
        return html.replace('data-bs-theme=""', `data-bs-theme="${theme}"`);
      }
    });
  }

  return resolve(event);
}

/** @type {import('@sveltejs/kit').HandleServerError} */
export async function handleError({ error, event, status, message }) {
  const errorId = crypto.randomUUID();

  console.error(error)

  return {
    message: 'Whoops!',
    errorId
  };
}
