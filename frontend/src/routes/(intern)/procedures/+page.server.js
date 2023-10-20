import * as Config from '$lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, fetch }) {

  const response = await fetch('api/procedures');
  const data = await response.json()

  return {
    procedures: data,
  };
}
