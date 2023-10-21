import * as Config from '$lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, fetch }) {

  const response = await fetch('http://localhost:8080/api/procedures');
  const data = await response.json()

  console.log(data)

  return {
    procedures: data,
  };
}
