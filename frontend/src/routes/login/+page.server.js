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

    const body = await api.post('api/auth/login', {
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
    cookies.set('jwt', value, { path: '/' });

    throw redirect(307, '/dashboard');
  }
};
