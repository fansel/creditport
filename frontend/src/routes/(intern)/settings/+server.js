import { json } from '@sveltejs/kit';

/** @type {import('../$types').RequestHandler} */
export async function POST({ request, cookies }) {
  const data = await request.json();

  const darkMode = data.darkMode;
  const useSystemMode = data.useSystemMode;
  const pageCount = data.pageCount;

  cookies.set('pageCount', pageCount, { path: '/' });
  cookies.set('useSystemMode', useSystemMode, { path: '/' });

  if (darkMode) {
    cookies.set('theme', 'dark', { path: '/' });
  } else {
    cookies.set('theme', 'light', { path: '/' });
  }

  return json({ success: true });
}
