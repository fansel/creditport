import * as api from '$lib/api.js';
import { zfd } from 'zod-form-data';
import z from 'zod';



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
      ['fremduni' + i]: zfd.text(),
      ['fremdstudiengang' + i]: zfd.text(),
      ['fremdstudiengangName' + i]: zfd.text(),
      ['lp' + i]: zfd.text(), // vielleicht besser als Zahl
      ['modulbeschreibung' + i]: zfd.file(z.instanceof(File).optional()),
      ['website' + i]: zfd.text(),
      ['studiengangUniLeipzig' + i]: zfd.text(),
      ['kommentar' + i]: zfd.text(),
    };
    entries.push(entry);
  }
  return entries;
}


export const actions = {
  requests: async ({ request }) => {
    const formData = await request.formData();

    const modulesCount = formData.get('modulesCount') || 0;

    console.log(modulesCount)

    // console.log(createMultipleEntries(2))

    const schema = zfd.formData({
      entries: createMultipleEntries(1)
    })
    
    // const result = schema.safeParse(formData);

    // if (!result.success){
    //     const data = {
    //         data: Object.fromEntries(formData),
    //         errors: result.error.flatten().fieldErrors
    //       };
    //   return fail(400, data)  
    // }

    // console.log(result.data)

    //result.data.text
    //result.data.pdf -> 


    // const fileUpload1 = formData();
    // test.append('file', result.data.file);
    // test.append('id', id)

    // //http://localhost:8080/api/v1/ + path
    // const res = await api.post('', test, null, { req_type: api.content_type.json , res_type: api.content_type.json})



    // console.log(res)

    // return { success: true }




  }
}

