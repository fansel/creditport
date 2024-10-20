import { json } from '@sveltejs/kit';
import * as config from '$lib/config.js';
import { setFlash } from 'sveltekit-flash-message/server';

/** @type {import('../$types').RequestHandler} */
export async function POST({ request, cookies }) {
  const data = await request.json();

  const darkMode = data.darkMode;
  const useSystemMode = data.useSystemMode;
  const pageCount = data.pageCount;

  cookies.set('pageCount', pageCount, { secure: config.secure_connection, path: '/' });
  cookies.set('useSystemMode', useSystemMode, { secure: config.secure_connection, path: '/' });

  if (darkMode) {
    cookies.set('theme', 'dark', { secure: config.secure_connection, path: '/' });
  } else {
    cookies.set('theme', 'light', { secure: config.secure_connection, path: '/' });
  }

  setFlash({type: 'success', message: 'Erfolgreich gespeichert'}, cookies)
  return json({ success: true });
}
