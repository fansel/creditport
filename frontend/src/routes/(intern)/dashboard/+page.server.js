import * as api from '$lib/api.js';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals }) {
  //Testen ob Nutzer autentifiziert
  const res = await api.get('api/v1/test-endpoint/secure', locals.user?.token, api.content_type.plain);

  return {
    title: 'Dashboard'
  };
}
