import * as api from '$lib/api.js';
import * as config from '$lib/config.js';
import { parseJwt } from '$root/lib/util';
import { message, setError, superValidate } from 'sveltekit-superforms';
import { login_schema } from '$root/lib/schema';
import { zod } from 'sveltekit-superforms/adapters';
import { redirect } from 'sveltekit-flash-message/server';

/**@type {import('./$types').Actions} */
export async function load({ locals }) {
  if (locals.user) throw redirect(307, '/dashboard');

  return {
    form: await superValidate(zod(login_schema))
  };
}

/**@type {import('./$types').Actions} */
export const actions = {
  login: async ({ cookies, request }) => {
    const form = await superValidate(request, zod(login_schema));

    if (!form.valid) {
      return message(form, { type: 'error', message: 'Bitte gebe deine Zugangsdaten ein!' }, { status: 400 });
    }

    const res = await api.post(api.routes.login, form.data);

    // TODO => nur auf res.success umstellen
    if (!res.success | !res.data.success) {
      setError(form, 'username', '')
      setError(form, 'password', '')

      return message(form, { type: 'error', message: 'Diese Zugangsdaten sind falsch.' }, { status: 400 });
    }

    const token = res.data.token;

    const user = {
      token,
      username: parseJwt(token).sub,
      expires_at: new Date(Number(parseJwt(token).exp * 1000)),
      role: parseJwt(token).role
    };

    const maxAge = Math.floor((user.expires_at.getTime() - new Date().getTime()) / 1000);

    const value = btoa(JSON.stringify(user));
    cookies.set('jwt', value, { secure: config.secure_connection, path: '/', maxAge: maxAge });

    throw redirect(307, '/dashboard', { type: 'success', message: 'Erfolgreich eingeloggt' }, cookies);
  }
};
