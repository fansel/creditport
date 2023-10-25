import * as config from '$lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const uuid = params.uuid;

  return {
    uuid
  };
}