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

//Reihenfolge ist wichtig wird mit STATUS[status] aufgerufen
export const STATUS = [
  //Vorgänge
  { text_intern: 'Neu', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'NEU' },
  { text_intern: 'Offen', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'OFFEN' },
  { text_intern: 'in Bearbeitung', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'IN_BEARBEITUNG' },
  { text_intern: 'Weitergeleitet', color: '#6c757d', text_color: 'white', text_extern: 'in Bearbeitung', match: 'WEITERGELEITET' },
  { text_intern: 'Vollständig', color: '#6c757d', text_color: 'white', text_extern: 'Vollständig', match: 'VOLLSTÄNDIG' },
  { text_intern: 'Nicht bearbeitet', color: '#6c757d', text_color: 'white', text_extern: 'Nicht bearbeitet', match: 'NICHT_BEARBEITET' },
  { text_intern: 'Bearbeitet', color: '#6c757d', text_color: 'white', text_extern: 'Bearbeitet', match: 'BEARBEITET' },
  { text_intern: 'Rückfrage nötig', color: '#6c757d', text_color: 'white', text_extern: 'Rückfrage nötig', match: 'RÜCKFRAGE_NÖTIG' },
  { text_intern: 'Angenommen', color: '#198754', text_color: 'white', text_extern: 'Angenommen', match: 'ANGENOMMEN' },
  { text_intern: 'Abgelehnt', color: '#dc3545', text_color: 'white', text_extern: 'Abgelehnt', match: 'ABGELEHNT' }
];

export function getStatus(match) {
  return STATUS.find((status) => status.match === match) || null;
}
