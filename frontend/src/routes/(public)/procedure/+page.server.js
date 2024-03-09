import * as api from '$lib/api.js';
//import { file, zfd } from 'zod-form-data';
import z from 'zod';
import { fail, redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const modules = await api.get('modules');
  const universities = await api.get('universities');
  return {
    universities: universities,
    modules: modules,
    title: 'Vorgang erstellen'
  };
}


function createDynamicSchema(count) {
  const dynamicFields = {};

  for (let i = 0; i < count; i++) {
    const creditPoints = `creditPoints${i}`;
    const externalModule = `externalModule${i}`;
    const internalModule = `internalModule${i}`;
    const moduleLink = `moduleLink${i}`;
    const annotation = `annotation${i}`;
    const formFile = `formFile${i}`;

    dynamicFields[creditPoints] = zfd.numeric(); // Hier kannst du den Validatortyp anpassen
    dynamicFields[externalModule] = zfd.text();
    dynamicFields[internalModule] = zfd.text();
    dynamicFields[moduleLink] = zfd.text(z.string().optional());
    dynamicFields[annotation] = zfd.text(z.string().optional());
    dynamicFields[formFile] = zfd.file(z.instanceof(File).optional());
  }

  return zfd.formData({
    globalAnnotation: zfd.text(z.string().optional()),
    university: zfd.text(),
    externalCourseName: zfd.text(),
    ...dynamicFields
  });
}

/** @type {import('./$types').Actions} */
export const actions = {
  requests: async ({ request }) => {
    const formData = await request.formData();

    const modulesCount = formData.get('modulesCount') || 0;
    const schema = createDynamicSchema(modulesCount);
    const result = schema.safeParse(formData);

    if (!result.success) {
      // const data = {
      //   data: Object.fromEntries(formData),
      //   errors: result.error.flatten().fieldErrors
      // };
      return fail(400, {});
    }

    function createBody(count) {
      const bodyFields = [];

      for (let i = 0; i < count; i++) {
        const field = {
          externalModule: result.data[`externalModule${i}`],
          internalModule: result.data[`internalModule${i}`],
          annotation: result.data[`annotation${i}`],
          creditPoints: result.data[`creditPoints${i}`],
          moduleLink: result.data[`moduleLink${i}`]
        };

        bodyFields.push(field);
      }

      return {
        annotation: result.data.globalAnnotation,
        university: result.data.university,
        courseName: result.data.externalCourseName,
        requests: bodyFields
      };
    }

    const body = createBody(modulesCount);

    const res = await api.post('procedures', body, null, { req_type: api.content_type.json, res_type: api.content_type.json });

    console.log(res);

    for (let i = 0; i < modulesCount; i++) {
      // Nur wenn eine PDF vorhanden ist soll auch eine abgeschickt werden
      if (result.data[`formFile${i}`]) {
        const fileUpload = new FormData();
        fileUpload.append('file', result.data[`formFile${i}`]);
        const resPDF = await api.post(`pdf/upload/${res.requests[i].requestId}`, fileUpload, null, { req_type: api.content_type.form_data, res_type: api.content_type.plain });
        console.log(resPDF);
      }
    }

    throw redirect(302, `/status/${res.procedureId}`);
    // return { success: true, procedureId: res.procedureId };
    //zugriff $page.form.procesureId
  }
};
