import * as api from '$lib/api.js';

/** @type {import('./$types').LayoutServerLoad} */
export async function load() {
  //Hier wird ein Request ans Backend gesendet um zu prüfen ob es ONLINE ist
  const res = await api.get(api.routes.system_health);

  if (res.success && res.data.status === 'UP') {
    return {
      status: true
    };
  }

  return {
    status: false
  };
}
