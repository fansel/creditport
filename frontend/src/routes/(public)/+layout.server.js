import * as api from '$lib/api.js';

/** @type {import('./$types').LayoutServerLoad} */
export async function load() {
  try {
    const backend_data = (await api.get('modules')) || [];
    let status = false;

    if (backend_data.find((item) => item.moduleName === 'Schlüsselqualifikation (Wahlpflichtfach)')) {
      status = true;
    }

    return {
      status
    };
  } catch (e) {
    console.log('API ist down');
  }

  return {
    status: false
  };
}
