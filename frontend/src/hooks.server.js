/** @type {import('@sveltejs/kit').Handle} */

/** @type {import('@sveltejs/kit').Handle} */
export async function handle({ event, resolve }) {
  const jwt = event.cookies.get('jwt');
  const theme = event.cookies.get('theme');

  event.locals.user = jwt ? JSON.parse(atob(jwt)) : null;

  if (theme) {
    console.log(theme);
    return await resolve(event, {
      transformPageChunk: ({ html }) => {
        return html.replace('data-bs-theme=""', `data-bs-theme="${theme}"`);
      }
    });
  }

  return resolve(event);
}
