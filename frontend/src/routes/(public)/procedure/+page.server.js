import * as api from '$lib/api.js';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const modules = await api.get('modules');

  return {
    modules: modules
  };
}
