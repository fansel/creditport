import { error } from '@sveltejs/kit';
import * as config from '$lib/config';

export const content_type = {
  plain: 'text/plain',
  json: 'application/json',
  form_data: 'multipart/form-data'
};

/**
 * Übersicht aller API Routes
 * Form: ohne Backslash am Anfang localhost:8080/users => users
 * TODO alle ergänzen
 */
export const routes = {
  /**
   * ----------------------------------------------------------------------
   * USERMANAGMENT
   * ----------------------------------------------------------------------
   */
  login: 'auth/login',
  register: 'users/register',
  user_update_password: 'users/update/password',
  user_all: 'users',
  user_by_id: (id) => `users/${id}`,

  /**
   * ----------------------------------------------------------------------
   * UNIVERSITIES
   * ----------------------------------------------------------------------
   */
  university_by_id: (id) => `universities/${id}`, // GET, PUT, DELETE
  university_all: `universities`, // POST, GET
  university_import: `universities/import`, // POST

  /**
   * ----------------------------------------------------------------------
   * PROCEDURES
   * ----------------------------------------------------------------------
   */
  procedure_by_id: (id) => `procedures/${id}`, // PUT, DELETE, GET
  procedure_all: 'procedures', // POST, GET
  procedure_ids: `procedures/ids`, // GET
  procedure_forward: (id) => `procedures/forward/${id}`, // POST
  procedure_archive: (id) => `procedures/archive/${id}`, // POST

  /**
   * ----------------------------------------------------------------------
   * REQUEST
   * ----------------------------------------------------------------------
   */
  request_by_id: (id) => `requests/${id}`, // PUT, DELETE, GET
  request_by_id_similar: (id) => `requests/similar/${id}`, // GET
  request_by_id_related: (id) => `requests/related/${id}`, // GET
  request_by_id_approval: (id) => `requests/approval/${id}`, // PUT
  request_all: 'requests', // POST, GET
  request_by_procedure_id: (id) => `requests/procedure/${id}`, // GET

  /**
   * ----------------------------------------------------------------------
   * MODULES
   * ----------------------------------------------------------------------
   */
  module_all_internal: 'modules/internal', // POST, GET
  module_all_external: `modules/external`, // POST, GET
  module_internal_import: `modules/internal/import`, // POST
  module_external_import: `modules/external/import`, // POST
  module_internal_by_id: (id) => `modules/internal/${id}`, // GET, PUT, DELETE
  module_external_by_id: (id) => `modules/external/${id}`, // GET, PUT, DELETE

  /**
   * ----------------------------------------------------------------------
   * COURSES
   * ----------------------------------------------------------------------
   */
  course_by_id: (id) => `courses/${id}`, // GET, PUT, DELETE
  course_all: `courses`, // POST, GET
  course_import: `courses/import`, // POST

  /**
   * ----------------------------------------------------------------------
   * SYSTEM
   * ----------------------------------------------------------------------
   */
  system_health: `actuator/health` // GET
};

/**
 * Methode zum senden von Requests an die API
 *
 * @param req_type Request Type - Content Type mit dem der Request gesendet wird (default json)
 * @param res_type Response Type - Content Type der in der Response erwartet wird (default json)
 * @returns
 */
async function send({ method, path, data, token, req_type = content_type.json, res_type = content_type.json }) {
  const opts = { method, headers: {} };

  if (data) {
    switch (req_type) {
      case content_type.json:
        opts.body = JSON.stringify(data);
        opts.headers['Content-Type'] = req_type;

        break;
      case content_type.plain:
        opts.body = data;
        opts.headers['Content-Type'] = req_type;

        break;
      case content_type.form_data:
        opts.body = data;
        opts.headers['Content-Type'] = req_type;

        break;
    }
  }

  if (token) {
    opts.headers['Authorization'] = `Bearer ${token}`;
  }

  try {
    const res = await fetch(`${config.api_endpoint}/${path}`, opts);
    let body = null;

    switch (res_type) {
      case content_type.json:
        body = await res.json();
        break;
      case content_type.plain:
        body = await res.text();
        break;
      case content_type.form_data:
        body = await res.formData();
      default:
        body = null;
    }

    return {
      success: res.ok,
      http_code: res.status,
      data: body
    };
  } catch (msg) {
    console.log(msg);
    return {
      success: false,
      http_code: 500,
      data: null
    };
  }
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
