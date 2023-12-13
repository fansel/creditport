import { error } from '@sveltejs/kit';
import * as config from '$lib/config';

export const content_type = {
  plain: 'text/plain',
  json: 'application/json',
  form_data: 'multipart/form-data'
};

/**
 * @param req_type Content Typ mit dem der request gesendet wird (muss immer gesetzt sein)
 * @param res_type Content Typ der von der response erwartet wird (zb. application/json)
 * @returns
 */
async function send({ method, path, data, token, req_type = content_type.json, res_type = content_type.json }) {
  const opts = { method, headers: {} };

  if (data) {
    opts.headers['Content-Type'] = req_type;
    switch (req_type) {
      case content_type.json:
        opts.body = JSON.stringify(data);
        break;
      case content_type.plain:
        opts.body = data;
        break;
      case content_type.form_data:
        const formData = new FormData();
        // Assume data is a plain object
        Object.entries(data).forEach(([key, value]) => {
          formData.append(key, value);
        });
        opts.body = formData;
        break;
    }
  }

  if (token) {
    opts.headers['Authorization'] = `Bearer ${token}`;
  }

  const res = await fetch(`${config.api_endpoint}/${path}`, opts);

  if (res.ok || res.status === 422) {
    //Hier fehlt noch Multipart
    switch (res_type) {
      case content_type.json:
        return await res.json();
      case content_type.plain:
        return await res.text();
      case content_type.form_data:
        return await res.formData();
      default:
        return null;
    }
  }

  return res.status;
}

/**
 * @example get('modules', locals.user?.token, { res_type: content_type.plain, req_type: content_type.json })
 * @returns
 */
export function get(path, token, opts = {}) {
  return send({ method: 'GET', path, token, ...opts });
}

export function del(path, token, opts = {}) {
  return send({ method: 'DELETE', path, token, ...opts });
}

export function post(path, data, token, opts = {}) {
  return send({ method: 'POST', path, data, token, ...opts });
}

export function put(path, data, token, opts = {}) {
  return send({ method: 'PUT', path, data, token, ...opts });
}
