import z from 'zod';
import { user_roles, status_requests } from '$lib/config';
/**
 * Alle globalen ZOD Schemas für Superform Integration
 */

export const login_schema = z.object({
  username: z.string(),
  password: z.string()
});

export const user_schema = z.object({
  username: z.string().min(1, { message: 'Name darf nicht leer sein.' }),
  role: z.enum(Object.values(user_roles)),
  userId: z.number()
});

export const add_user_schema = user_schema.extend({
  password: z.string().min(1, { message: 'Passwort darf nicht leer sein.' })
});

export const universities_schema = z.object({
  uniId: z.string().min(1),
  uniName: z.string().min(1, { message: 'Uni Name darf nicht leer sein.' }),
  verified: z.boolean().default(false)
});

export const universities_upload_schema = z.object({
  file: z.instanceof(File, { message: 'Please upload a file.' }).refine((f) => f.size < 100_000, 'Max 100 kB upload size.')
});

export const universities_import_schema = z.array(
  z.object({
    uniName: z.string(),
    verified: z.boolean()
  })
);

export const update_course_schema = z.object({
  courseName: z.string().min(1, { message: 'Name des Studiengangs darf nicht leer sein!' }),
  courseId: z.string().min(1)
});

export const add_course_schema = z.object({
  courseName: z.string().min(1, { message: 'Name des Studiengangs darf nicht leer sein!' })
});

export const update_internal_modul_schema = z.object({
  moduleId: z.string(),
  number: z.string(),
  moduleName: z.string().min(1, { message: 'Name des Moduls darf nicht leer sein!' }),
  moduleDescription: z.string(),
  creditPoints: z.number().default(1)
});

export const update_external_module = z.object({
  moduleId: z.string(),
  moduleNumber: z.string(),
  moduleName: z.string(),
  moduleDescription: z.string(),
  university: universities_schema,
  creditPoints: z.number(),
  verified: z.boolean(),
})

export const full_request_schema = z.object({
  procedureId: z.number(),
  requestId: z.number(),
  externalModules: z.array(update_external_module),
  internalModules: z.array(z.object({moduleId: z.string()})),
  annotationStudent: z.string(),
  annotationCommittee: z.string(),
  statusRequest: z.enum(status_requests.map((status) => status.match)),
  createdAt: z.string(),
  pdfExists: z.boolean(),
  moduleLink: z.string()
});

export const request_schema = z.object({
  procedureId: z.number(),
  requestId: z.number(),
  externalModuleIds: z.array(z.string()),
  internalModulesIds: z.array(z.string()),
  annotationStudent: z.string(),
  annotationCommittee: z.string(),
  statusRequest: z.enum(status_requests.map((status) => status.match)),
  createdAt: z.string(),
  pdfExists: z.boolean(),
  moduleLink: z.string()
});
