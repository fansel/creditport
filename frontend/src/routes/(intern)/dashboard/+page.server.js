import * as api from '$lib/api.js';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals }) {
  //Testen ob Nutzer autentifiziert
  const res = await api.get('test-endpoint/secure', locals.user?.token, { res_type: api.content_type.plain });

  return {
    title: 'Dashboard'
  };
}
