import * as Config from '$lib/config';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, fetch }) {
  // try {
  //   const response = await fetch('http://localhost:8080/api/procedures');

  //   if (!response.ok) {
  //     throw new Error(`API-Fehler: ${response.statusText}`);
  //   }

  //   const data = await response.json();

  //   return {
  //     procedures: data,
  //   };
  // } catch (e) {

  //   console.log(e)

  //   throw error(500, "Fehler beim Laden des API-Endpoints!");
  // }

  let data = { data: [{ university: 'test', course: 'test', requestCount: 5 }] };

  return { procedures: data };
}
