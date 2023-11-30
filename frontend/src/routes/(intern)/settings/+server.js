import { json } from '@sveltejs/kit';

/** @type {import('../$types').RequestHandler} */
export async function POST({ request, cookies }) {
  const data = await request.json();

  const darkMode = data.darkMode;
  const useSystemMode = data.useSystemMode;
  const pageCount = data.pageCount;

  cookies.set('pageCount', pageCount, { secure: false, path: '/' });
  cookies.set('useSystemMode', useSystemMode, { secure: false, path: '/' });

  if (darkMode) {
    cookies.set('theme', 'dark', { secure: false, path: '/' });
  } else {
    cookies.set('theme', 'light', { secure: false, path: '/' });
  }

  return json({ success: true });
}
