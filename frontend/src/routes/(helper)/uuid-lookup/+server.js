import { json } from '@sveltejs/kit'
import * as api from '$lib/api.js'

// Prüft ob eine spezielle Vorgangsnummer exisitert, sendet true wenn ja, sonst false
/** @type {import('./$types').RequestHandler} */
export async function POST(event) {
  const data = await event.request.json()
  const uuid = data.uuid;

  const res = await api.get(`procedures/${uuid}`, null);

  // it's common to return JSON, so SvelteKit has a helper
  return json({ success: res != 404 ? true : false })
}