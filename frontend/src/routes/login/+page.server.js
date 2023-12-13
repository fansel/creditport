import { redirect, fail } from '@sveltejs/kit';
import * as api from '$lib/api.js';
import * as config from '$lib/config.js';
import { parseJwt } from '$root/lib/util';

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
      username: parseJwt(body.token).sub,
      expires_at: new Date(Number(parseJwt(body.token).exp * 1000)),
      role: parseJwt(body.token).role
    };

    const maxAge = Math.floor((user.expires_at.getTime() - new Date().getTime()) / 1000);

    const value = btoa(JSON.stringify(user));
    cookies.set('jwt', value, { secure: config.secure_connection, path: '/', maxAge: maxAge });

    throw redirect(307, '/dashboard');
  }
};
