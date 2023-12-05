import * as config from '$lib/config';
import * as api from '$lib/api';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params, locals }) {
  // const procedures = (await api.get('dto', locals.user?.token, api.content_type.json)) || [];

  const procedures = [
    {
      createdAt: '2023-12-05T16:07:48.655047',
      lastEditAt: '2023-12-05T16:07:48.655047',
      university: 'Universität Leipzig',
      course: 'Bachlor Informatik',
      status: 'NEU',
      annotation: 'Eine Bemerkung',
      requestCount: 1,
      procedureId: '123456',
      requests: [
        {
          id: 1,
          createdAt: '2023-12-05T16:07:48.655047',
          lastEditAt: '2023-12-05T16:07:48.655047',
          annotation: 'Eine Bemerkung',
          cp: 5,
          status: 'NEU',
          externalModule: {
            id: 10,
            name: 'Modellierung und Programmierung 1'
          },
          internalModule: {
            id: 11,
            name: 'Technische Informatik 1'
          }
        }
      ]
    }
  ];

  return { procedures, title: 'Vorgänge' };
}

export const ssr = false;
