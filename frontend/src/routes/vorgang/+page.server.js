import * as Config from '$lib/config';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  return {
    modules: Config.module_json
  };
}
