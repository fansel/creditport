import { json } from '@sveltejs/kit'
import * as api from '$lib/api.js'

// Prüft ob eine spezielle Vorgangsnummer exisitert, sendet true wenn ja, sonst false
/** @type {import('./$types').RequestHandler} */
export async function POST(event) {
  const data = await event.request.json()
  const request = data;

  const res = await api.put(`requests/${request.requestId}`, request, event.locals.user?.token);

  // it's common to return JSON, so SvelteKit has a helper
  return json({ success: true })
}