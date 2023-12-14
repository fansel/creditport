import * as api from '$lib/api.js';

/** @type {import('./$types').PageLoad} */
export async function load({ params, locals }) {
  //Testen ob Nutzer autentifiziert
  const res = await api.get('test-endpoint/secure', locals.user?.token, { res_type: api.content_type.plain });

  const procedures = await api.get('procedures', locals.user?.token);

  const offen = procedures.length;
  const archiviert = 0;
  const in_bearbeitung = 0;

  return {
    title: 'Dashboard',
    procedures,
    open_procedures: offen,
    archived_procedures: archiviert,
    processing_procdures: in_bearbeitung
  };
}
