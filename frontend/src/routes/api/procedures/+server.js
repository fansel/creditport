import { json } from '@sveltejs/kit';

const procedures_data = {
  data: 
  [
    {
      createdAt : new Date(),
      university : "Universität Leipzig",
      course : "Informatik Bachelor",
      status : 1,
      requestCount : 5,
    },
    {
      createdAt : new Date(),
      university : "Universität Leipzig",
      course : "Physik Bachelor",
      status : 1,
      requestCount : 5,
    },
  ],
  count: 2,
};

/** @type {import('./$types').RequestHandler} */
export function GET({ request }) {

	return json(procedures_data);
}