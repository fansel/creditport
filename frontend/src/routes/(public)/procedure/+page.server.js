import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import z from 'zod';
import { fail } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const modules = await api.get('modules');

  return {
    modules: modules
  };
}

/** @type {import('./$types').Actions} */
//Einträge
function createMultipleEntries(numEntries) {
  const entries = [];
  for (let i = 0; i < numEntries; i++) {
    const entry = {
      ['creditPoints' + i]: zfd.text(), // vielleicht besser als Zahl
      // ['modulbeschreibung' + i]: zfd.file(z.instanceof(File).optional()),
      ['externalModule' + i]: zfd.text(),
      ['internalModule' + i]: zfd.text(),
      ['moduleWbsite' + i]: zfd.text(),
      ['annotation' + i]: zfd.text()
    };
    entries.push(entry);
  }
  return entries;
}

export const actions = {
  requests: async ({ request }) => {
    const formData = await request.formData();

    const modulesCount = formData.get('modulesCount') || 0;

    console.log(modulesCount);

    // console.log(createMultipleEntries(2))

    const schema = zfd.formData({
      globalAnnotation: zfd.text()
      //university: zfd.text(),
      //courseName: zfd.text()
      // ...createMultipleEntries(1),
    });

    const result = schema.safeParse(formData);
    console.log(result.success);
    if (!result.success) {
      const data = {
        data: Object.fromEntries(formData),
        errors: result.error.flatten().fieldErrors
      };
      console.log(data);
      return fail(400, data);
    }

    // console.log(result.data)

    //result.data.text
    //result.data.pdf ->

    //const fileUpload1 = { file: result.data.file };

    //http://localhost:8080/api/v1/ + path
    //const body = result.data
    const body = {
      annotation: 'test',
      university: 'test1',
      courseName: 'test2',
      requests: [
        {
          externalModule: 'Module 1',
          internalModule: 'Internal Module 1',
          annotation: 'Test annotation for request 1',
          creditPoints: 5
        }
      ]
    };

    const res = await api.post('procedures', body, null, { req_type: api.content_type.json, res_type: api.content_type.json });
    console.log(res);

    return { success: true, procedureId: res.procedureId };
    //zugriff $page.form.procesureId
  }
};
