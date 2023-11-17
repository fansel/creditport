import * as api from '$lib/api.js';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals }) {
  //Testen ob Nutzer autentifiziert
  const userlist = await api.get('api/usermanagement/users', locals.user?.token, api.content_type.json);

  return {
    title: 'Dashboard',
    users: userlist
  };
}
