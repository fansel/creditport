import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import { fail } from '@sveltejs/kit';
import z from 'zod';

/** @type {import('./$types').PageServerLoad} */
export async function load({ locals }) {
  let userlist;

  //Muss später noch auf die Rolle angepasst werden #TODO
  if (locals.user.username == 'admin') {
    //Nutzerliste
    userlist = await api.get('usermanagement/users', locals.user?.token, api.content_type.json);
  }

  return { title: 'Einstellungen', users: userlist, subtitle: 'Benutzer & Rollen' };
}
