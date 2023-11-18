import * as api from '$lib/api.js';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals }) {
  let userlist;
  let unilist;

  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.username == 'admin') {
    //Nutzerliste
    userlist = await api.get('api/usermanagement/users', locals.user?.token, api.content_type.json);
    unilist = await api.get('api/universities', null , api.content_type.json);
  }

  return {
    title: 'Dashboard',
    users: userlist,
    universities: unilist
  };
}
