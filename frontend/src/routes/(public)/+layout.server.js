import * as api from '$lib/api.js';

/** @type {import('./$types').LayoutServerLoad} */
export async function load() {
  //Hier wird ein Request ans Backend gesendet um zu prüfen ob es ONLINE ist
    const backend_data = (await api.get('actuator/health')) || [];
    let status = false;

    if (backend_data.status === 'UP') {
      status = true;
    }

  return {
    status: false
  };
}
