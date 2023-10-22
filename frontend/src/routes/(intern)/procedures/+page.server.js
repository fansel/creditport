import * as Config from '$lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, fetch }) {

  try {
    const response = await fetch('http://localhost:8080/api/procedures');
    const data = await response.json()
  } catch (error) {
    
  } 

  console.log(data)

  return {
    procedures: data,
  };
}
