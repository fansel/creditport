import * as api from '$lib/api.js';
import { file, zfd } from 'zod-form-data';
import z from 'zod';
import { fail, redirect, error } from '@sveltejs/kit';
import { superValidate } from 'sveltekit-superforms/server';
import { zod } from 'sveltekit-superforms/adapters';

const addUniversitySchema = z.object({
    universityName: z.string().min(1)
   });






/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  // const addUniversityForm = await superValidate(zod(addUniversitySchema));
  // console.log(addUniversityForm)
  const universities = await api.get(api.routes.university_all);
  if(!universities.success){
    throw error(404, { message: 'Fehler beim Laden der Universitäten' });
  }

  const internalModules = await api.get(api.routes.module_all_internal);
  if (!internalModules.success) {
    throw error(404, { message: 'Fehler beim Laden der internen Module' });
  }

  const externalModules = await api.get(api.routes.module_all_external);
  if (!externalModules.success) {
    throw error(404, { message: 'Fehler beim Laden der externen Module' });
  }

  const courses = await api.get(api.routes.course_all);
  if (!courses.success){
    throw error(404, { message: 'Fehler beim Laden der Studiengänge' });
  }
  return {
    universities: universities.data,
    internalModules: internalModules.data,
    externalModules: externalModules.data,
    courses: courses.data,
    title: 'Vorgang erstellen',
   // addUniversityForm
  };
}

/** @type {import('./$types').Actions} */
export const actions = {
  requests: async ({ request }) => {
    const formData = await request.formData();

    function createDynamicSchema(count) {
      const dynamicFields = {};

      for (let i = 0; i < count; i++) {
        const internalModulesCount = formData.get(`internalModulesCount${i}`) || 0;
        const externalModulesCount = formData.get(`externalModulesCount${i}`) || 0;
        for (let j = 0; j < internalModulesCount; j++) {
          const internalModuleId = `internalModuleId${i}-${j}`;
          dynamicFields[internalModuleId] = zfd.text().optional();
        }
        for (let j = 0; j < externalModulesCount; j++) {
          //const creditPoints = `creditPoints${i}-${j}`;
          const externalModuleId = `externalModuleId${i}-${j}`;

          // const moduleLink = `moduleLink${i}-${j}`;
          // const annotation = `annotation${i}-${j}`;
          // const formFile = `formFile${i}-${j}`;

          // dynamicFields[creditPoints] = zfd.numeric(); // Hier kannst du den Validatortyp anpassen
          dynamicFields[externalModuleId] = zfd.text().optional();

          // dynamicFields[moduleLink] = zfd.text(z.string().optional());
          // dynamicFields[annotation] = zfd.text(z.string().optional());
          // dynamicFields[formFile] = zfd.file(z.instanceof(File).optional());
        }
        const annotationStudent = `annotation${i}`;
        const moduleLink = `moduleLink${i}`;
        dynamicFields[annotationStudent] = zfd.text().optional();
        dynamicFields[moduleLink] = zfd.text().optional();
      }

      return zfd.formData({
        globalAnnotation: zfd.text(z.string().optional()),
        universityId: zfd.text().optional(),
        internalCourseId: zfd.text().optional(),
        ...dynamicFields
      });
    }

    const requestCount = formData.get('requestCount') || 0;
    const schema = createDynamicSchema(requestCount);
    //console.log(JSON.stringify(schema,null,2)+ "schema");
//console.log(schema+" schemaaa");
    //const form = await superValidate(request, schema);
    //console.log(form)
    const result = schema.safeParse(formData);
    console.log("result"+JSON.stringify(result, null, 2)+ " result");
    //console.log(result.data.globalAnnotation);

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
        const internalModulesCount = formData.get(`internalModulesCount${i}`) || 0;
        const externalModulesCount = formData.get(`externalModulesCount${i}`) || 0;
        const externalModuleFields = [];
        const internalModuleFields = [];
        for(let j = 0; j<internalModulesCount; j++){
          
             //result.data[`internalModuleId${i}-${j}`],
          
          internalModuleFields.push(result.data[`internalModuleId${i}-${j}`]);
        }
        for(let j=0; j<externalModulesCount; j++){
          externalModuleFields.push(result.data[`externalModuleId${i}-${j}`]);
        }

        const field = {
          externalModuleId: externalModuleFields,
          internalModuleId: internalModuleFields,
          annotationStudent: result.data[`annotation${i}`],
          moduleLink: result.data[`moduleLink${i}`]
       
          // externalModule: result.data[`externalModule${i}`],
          // internalModule: result.data[`internalModule${i}`],
          // annotation: result.data[`annotation${i}`],
          // creditPoints: result.data[`creditPoints${i}`],
          // moduleLink: result.data[`moduleLink${i}`]
        };

        bodyFields.push(field);
      }

      return {
        annotation: result.data.globalAnnotation,
        universityId: result.data.universityId,
        courseId: result.data.internalCourseId,
        requests: bodyFields
      };
    }

    const body = createBody(requestCount);
    console.log("body"+JSON.stringify(body, null, 2)+ "body");

    const res = await api.post(`procedures`, body, null, { req_type: api.content_type.json, res_type: api.content_type.json });

    console.log(res.data.procedureId+" pups");

    for (let i = 0; i < requestCount; i++) {
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
  },

  addUniversity: async ({ request }) => {
    // const addUniversityForm = await superValidate(request, zod(addUniversitySchema));
    // console.log(addUniversityForm);
    // if (!form.valid) {
    //   // Again, return { form } and things will just work.
    //   return fail(400, { form });
    // }
    const formData = await request.formData();

    const addUniversitySchema = zfd.formData({
      universityName: zfd.text()
    });

    const result = addUniversitySchema.safeParse(formData);

    if (!result.success) {
      const data = {
        data: Object.fromEntries(formData),
        errors: result.error.flatten().fieldErrors
      };
      return fail(400, data);
    }

    const body = {
      uniName: result.data.universityName
    };

    const res = await api.post(`universities`, body, null, { req_type: api.content_type.json, res_type: api.content_type.json });
    
    let uniId = res.data.uniId;
    let uniName = res.data.uniName;

    return { success: true, uniId: uniId, uniName: uniName};
  },

  addModul: async ({ request }) => {
    const formData = await request.formData();
  
  }
};
