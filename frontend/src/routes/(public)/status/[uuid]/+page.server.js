import * as config from '$lib/config';
import * as api from '$lib/api';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const uuid = params.uuid;

  const procedure = await api.get(`procedures/${uuid}`);

  if (!procedure || procedure == 404) {
    throw error(404, 'Dieser Vorgang ist leider nicht vorhanden!');
  }

  return {
    uuid,
    procedure
  };
}
