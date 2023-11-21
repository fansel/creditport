import { error } from '@sveltejs/kit';
import * as config from '$lib/config';

export const content_type = {
  plain: 'text/plain',
  json: 'application/json',
  form_data: 'multipart/form-data'
};

async function send({ method, path, data, token, content }) {
  const opts = { method, headers: {} };

  if (data) {
    opts.headers['Content-Type'] = content;
    opts.body = JSON.stringify(data); //Hier muss noch auf die anderen Content Types eingegangen werden
  }

  if (token) {
    opts.headers['Authorization'] = `Bearer ${token}`;
  }

  const res = await fetch(`${config.api_endpoint}/${path}`, opts);

  if (res.ok || res.status === 422) {
    let ret = {};

    //Hier fehlt noch Multipart
    switch (content) {
      case content_type.json:
        ret = await res.json();

        break;
      case content_type.plain:
        ret = await res.text();

        break;
      // case content_type.form_data:
      //     re
  
      //     break;
      default:
        break;
    }
    return ret;
  }

  throw error(res.status);
}

export function get(path, token, content = content_type.json) {
  return send({ method: 'GET', path, token, content });
}

export function del(path, token, content = content_type.json) {
  return send({ method: 'DELETE', path, token, content });
}

export function post(path, data, token, content = content_type.json) {
  return send({ method: 'POST', path, data, token, content });
}

export function put(path, data, token, content = content_type.json) {
  return send({ method: 'PUT', path, data, token, content });
}
