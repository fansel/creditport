import { redirect, fail } from '@sveltejs/kit';
import * as api from '$lib/api.js';

/**@type {import('./$types').Actions} */
export async function load({ locals }) {
  if (locals.user) throw redirect(307, '/dashboard');
}

//TODO
// - Einen Button zum anzeigen des Passworts einbauen

/**@type {import('./$types').Actions} */
export const actions = {
  default: async ({ cookies, request }) => {
    const data = await request.formData();

    const body = await api.post('auth/login', {
      username: data.get('username'),
      password: data.get('password')
    });

    if (!body.success) {
      return fail(401, body);
    }

    const user = {
      token: body.token,
      username: JSON.parse(atob(body.token.split('.')[1])).sub,
      expires_at: new Date(Number(JSON.parse(atob(body.token.split('.')[1])).exp * 1000))
    };

    const value = btoa(JSON.stringify(user));
    cookies.set('jwt', value, { secure: false, path: '/' });

    throw redirect(307, '/dashboard');
  }
};
