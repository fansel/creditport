import { env } from '$env/dynamic/public';

export const title = 'Anrechungsportal';

export const api_endpoint = env.PUBLIC_API_ENDPOINT || 'http://backend:8080/api/v1';
export const secure_connection = env.PUBLIC_SECURE_CONNECTION === 'true';
export const pdf_endpoint = env.PUBLIC_PDF_ENDPOINT || 'http://localhost:8080/api/v1' + '/pdf/download/';

export const user_roles = {
  ADMIN: 'ADMIN',
  STUDY_OFFICE: 'STUDY_OFFICE',
  EXAM_COMITEE: 'EXAM_COMITEE'
};

// Status die ein Vorgang haben kann, Reihenfolge ist wichtig
export const status_procedures = [
  { text_intern: 'Neu', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'NEU' },
  { text_intern: 'Offen', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'OFFEN' },
  { text_intern: 'in Bearbeitung', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'IN_BEARBEITUNG' },
  { text_intern: 'Weitergeleitet', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'WEITERGELEITET' },
  { text_intern: 'Vollständig', color: '#6c757d', text_color: 'white', text_extern: 'Vollständig', match: 'VOLLSTÄNDIG' },
  { text_intern: 'Archiviert', color: '#6c757d', text_color: 'white', text_extern: 'Vollständig', match: 'ARCHIVIERT' } // Achtung, im Backend noch nicht implementiert
];

// Status die ein Antrag haben kann, Reihenfolge ist wichtig
export const status_requests = [
  { text_intern: 'Nicht bearbeitet', color: '#6c757d', text_color: 'white', text_extern: 'Nicht bearbeitet', match: 'NICHT_BEARBEITET' },
  { text_intern: 'Bearbeitet', color: '#6c757d', text_color: 'white', text_extern: 'Bearbeitet', match: 'BEARBEITET' },
  { text_intern: 'Rückfrage nötig', color: '#6c757d', text_color: 'white', text_extern: 'Rückfrage nötig', match: 'RÜCKFRAGE_NÖTIG' },
  { text_intern: 'Angenommen', color: '#198754', text_color: 'white', text_extern: 'Angenommen', match: 'ANGENOMMEN' },
  { text_intern: 'Abgelehnt', color: '#dc3545', text_color: 'white', text_extern: 'Abgelehnt', match: 'ABGELEHNT' }
];

export function getProcedureStatus(match) {
  return status_procedures.find((status) => status.match === match) || null;
}

export function getRequestStatus(match) {
  return status_requests.find((status) => status.match === match) || null;
}

/**
 * Überprüft, ob ein Datum zwischen zwei anderen Daten liegt, wobei null als das größte oder kleinste Datum betrachtet wird.
 * @param {Date} date - Das zu überprüfende Datum.
 * @param {Date} date1 - Das erste Grenzdatum.
 * @param {Date} date2 - Das zweite Grenzdatum.
 * @returns {boolean} - Gibt true zurück, wenn das Datum zwischen date1 und date2 liegt, andernfalls false.
 */
export function isDateBetween(date, date1, date2) {
  // Behandlung von null als größtes bzw. kleinste Datum
  if (date1 === null) date1 = new Date(Number.MIN_VALUE);
  if (date2 === null) date2 = new Date(Number.MAX_VALUE);

  // Überprüfen, ob das Datum zwischen date1 und date2 liegt
  return date >= date1 && date <= date2;
}
