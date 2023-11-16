import * as api from '$lib/api.js';

/** @type {import('./$types').LayoutServerLoad} */
export async function load() {
  //Hier wird ein Request ans Backend gesendet um zu prüfen ob es ONLINE ist
  try {
    const backend_data = (await api.get('modules')) || [];
    let status = false;

    if (backend_data.find((item) => item.moduleName === 'Schlüsselqualifikation (Wahlpflichtfach)')) {
      status = true;
    }

    return {
      status
    };

    //Bei einem Fehler wird einfach status: false zurückgegeben
  } catch (e) {
    console.log('API ist down');
  }

  return {
    status: false
  };
}
