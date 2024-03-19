import * as api from '$lib/api.js';
import { error, redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals }) {
  if (!locals.user) throw redirect(302, `/login`);

  // Testen ob Nutzer autentifiziert
  // const res = await api.get('test-endpoint/secure', locals.user?.token, { res_type: api.content_type.plain });

  const res = await api.get(api.routes.procedure_all, locals.user?.token);

  if (!res.success) {
    throw error(res.http_code, { message: 'Fehler beim Laden der Vorgänge' });
  }

  const offen = res.data.length;
  const archiviert = 0;
  const in_bearbeitung = 0;

  return {
    title: 'Dashboard',
    procedures: res.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)),
    open_procedures: offen,
    archived_procedures: archiviert,
    processing_procdures: in_bearbeitung
  };
}
