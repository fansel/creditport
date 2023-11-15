import { redirect, fail } from '@sveltejs/kit';
import * as api from '$lib/api.js';

/**@type {import('./$types').Actions} */
export async function load({ locals }) {
  if (locals.user) throw redirect(307, '/dashboard');
}

/**@type {import('./$types').Actions} */
export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData();

    const body = await api.post('auth/register', {
      username: data.get('username'),
      password: data.get('password')
    });

    if (body.errors) {
      return fail(401, body);
    }

    const value = btoa(JSON.stringify(body));
    cookies.set('jwt', value, { path: '/' });

    throw redirect(307, '/dashboard');
  }
};
