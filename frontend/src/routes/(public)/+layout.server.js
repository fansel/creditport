import * as api from '$lib/api.js';

/** @type {import('./$types').LayoutServerLoad} */
export async function load() {
  //Hier wird ein Request ans Backend gesendet um zu prüfen ob es ONLINE ist
  const backend_data = await api.get('actuator/health');

  if (backend_data && backend_data.status === 'UP') {
    return {
      status: true
    };
  }

  return {
    status: false
  };
}
