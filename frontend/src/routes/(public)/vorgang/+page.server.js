import * as config from '$lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const request = await fetch(`${config.api_endpoint}/modules`);
  const modules = await request.json();

  return {
    modules: modules
  };
}
