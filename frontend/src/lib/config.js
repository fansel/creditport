import { env } from '$env/dynamic/public';

export const title = 'Anrechungsportal';

export const api_endpoint = env.PUBLIC_API_ENDPOINT || 'http://backend:8080/api/v1';
export const secure_connection = env.PUBLIC_SECURE_CONNECTION === 'true';
export const pdf_endpoint = env.PUBLIC_PDF_ENDPOINT || 'http://localhost:8080/api/v1';

export const user_roles = {
  ADMIN: 'ADMIN',
  STUDY_OFFICE: 'STUDY_OFFICE',
  EXAM_COMITEE: 'EXAM_COMMITTEE'
};

// Status die ein Vorgang haben kann, Reihenfolge ist wichtig
export const status_procedures = [
  { text_intern: 'Neu', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'NEU', hide: true },
  { text_intern: 'Offen', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'OFFEN', hide: true },
  { text_intern: 'in Bearbeitung', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'IN_BEARBEITUNG', hide: true },
  { text_intern: 'Weitergeleitet', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'WEITERGELEITET', hide: true },
  { text_intern: 'Vollständig', color: '#6c757d', text_color: 'white', text_extern: 'Vollständig', match: 'VOLLSTAENDIG', hide: true },
  { text_intern: 'Archiviert', color: '#6c757d', text_color: 'white', text_extern: 'Vollständig', match: 'ARCHIVIERT', hide: true } // Achtung, im Backend noch nicht implementiert
];

// Status die ein Antrag haben kann, Reihenfolge ist wichtig
export const status_requests = [
  { text_intern: 'Nicht bearbeitet', color: '#6c757d', text_color: 'white', text_extern: 'Nicht bearbeitet', match: 'NICHT_BEARBEITET' },
  { text_intern: 'Bearbeitet', color: '#ffc107', text_color: 'white', text_extern: 'In Bearbeitung', match: 'BEARBEITET' },
  { text_intern: 'Rückfrage nötig', color: '#ffc107', text_color: 'white', text_extern: 'Rückfrage nötig', match: 'RÜCKFRAGE_NÖTIG' },
  { text_intern: 'Angenommen', color: '#198754', text_color: 'white', text_extern: 'Angenommen', match: 'ANGENOMMEN' },
  { text_intern: 'Abgelehnt', color: '#dc3545', text_color: 'white', text_extern: 'Abgelehnt', match: 'ABGELEHNT' }
];

export function getProcedureStatus(match) {
  return status_procedures.find((status) => status.match === match) || null;
}

export function getRequestStatus(match) {
  return status_requests.find((status) => status.match === match) || null;
}

