import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import { fail } from '@sveltejs/kit';
import z from 'zod';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals, cookies }) {
  let darkMode = cookies.get('theme') || 'light';
  let useSystemMode = cookies.get('useSystemMode') || false;
  let pageCount = cookies.get('pageCount') || 10;

  return {
    title: 'Einstellungen',
    subtitle: 'Allgemein',
    settings: {
      darkMode: darkMode,
      useSystemMode: useSystemMode,
      pageCount: pageCount
    }
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  changePassword: async ({ cookies, request }) => {
    const formData = await request.formData();

    const schema = zfd.formData({
      aktuellesPassword: zfd.text(z.string().optional()),
      neuesPassword: zfd.text(z.string().optional())
    });

    const result = schema.safeParse(formData);

    if (!result.success) {
      const data = {
        data: Object.fromEntries(formData),
        errors: result.error.flatten().fieldErrors,
        success: false
      };
      return fail(400, data);
    }

    return { success: true };
  }
};
