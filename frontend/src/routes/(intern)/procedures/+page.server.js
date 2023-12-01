import * as config from '$lib/config';
import * as api from '$lib/api';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, locals }) {
  const procedures = (await api.get('procedure', locals.user?.token, api.content_type.json)) || [];

  return { procedures, title: 'Vorgänge' };
}
