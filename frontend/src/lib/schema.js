import z from 'zod';
import { user_roles, status_requests, status_procedures } from '$lib/config';
/**
 * Alle globalen ZOD Schemas für Superform Integration
 */

export const login_schema = z.object({
  username: z.string(),
  password: z.string()
});

export const user_schema = z.object({
  username: z.string().min(1, { message: 'Name darf nicht leer sein.' }),
  password: z.string().min(1, { message: 'Passwort darf nicht leer sein.' }),
  role: z.enum(Object.values(user_roles)),
  userId: z.number()
});

export const add_user_schema = user_schema
  .extend({
    confirm_password: z.string()
  })
  .refine((data) => data.password == data.confirm_password, {
    message: 'Nicht das selbe Passwort.',
    path: ['confirm_password']
  });

export const register_user_schema = user_schema.omit({ userId: true });

export const universities_schema = z.object({
  uniId: z.string().min(1),
  uniName: z.string().min(1, { message: 'Uni Name darf nicht leer sein.' }),
  verified: z.boolean().default(false)
});

// export const add_university = z.object({
//   uniName: z.string().min(1, { message: 'Uni Name darf nicht leer sein.'})
// })

export const universities_upload_schema = z.object({
  file: z.instanceof(File, { message: 'Please upload a file.' }).refine((f) => f.size < 100_000, 'Max 100 kB upload size.')
});

export const courses_upload_schema = z.object({
  file: z.instanceof(File, { message: 'Please upload a file.' }).refine((f) => f.size < 100_000, 'Max 100 kB upload size.')
});

export const courses_import_schema = z.array(
  z.object({
    courseName: z.string(),
    modules: z.array(
      z.object({
        number: z.string(),
        moduleName: z.string().min(1),
        moduleDescription: z.string(),
        creditPoints: z.number()
      })
    )
  })
);

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
  id: z.string(),
  number: z.string(),
  moduleName: z.string().min(1, { message: 'Name des Moduls darf nicht leer sein!' }),
  moduleDescription: z.string(),
  creditPoints: z.number().default(1),
  courseIds: z.array(z.string()).min(1, { message: 'Es muss mindestens ein Studiengang ausgewählt sein.' })
});

export const update_external_module = z.object({
  moduleId: z.string(),
  moduleNumber: z.string(),
  moduleName: z.string(),
  moduleDescription: z.string(),
  university: universities_schema,
  creditPoints: z.number(),
  verified: z.boolean()
});

export const add_external_module = z.object({
  moduleNumber: z.string().optional(),
  moduleName: z.string().min(1),
  moduleDescription: z.string().optional(),
  // university: universities_schema,
  university: z.object({
    uniId: z.string(),
    uniName: z.string(),
    verified: z.boolean()
  }),
  creditPoints: z.number(),
})

export const add_university = z.object({
  uniName: z.string()
})

export const modules_import_schema = z.array(update_internal_modul_schema.omit({ id: true, courseIds: true }).extend({ moduleId: z.string(), courses: z.array(update_course_schema) }));

export const full_request_schema = z.object({
  procedureId: z.number(),
  requestId: z.number(),
  externalModules: z.array(update_external_module),
  internalModules: z.array(z.object({ moduleId: z.string() })),
  annotationStudent: z.string(),
  annotationCommittee: z.string(),
  statusRequest: z.enum(status_requests.map((status) => status.match)),
  createdAt: z.string(),
  pdfExists: z.boolean(),
  moduleLink: z.string().nullable()
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
  moduleLink: z.string().nullable()
});

export const course_schema = z.object({
  courseName: z.string(),
  courseId: z.string()
});

export const show_request_schema = z.object({
  procedureId: z.number(),
  requestId: z.number(),
  externalModules: z.array(update_external_module),
  internalModules: z.array(update_internal_modul_schema.omit({ courseIds: true, id: true })),
  annotationStudent: z.string(),
  annotationCommittee: z.string(),
  statusRequest: z.enum(status_requests.map((status) => status.match)),
  createdAt: z.string(),
  pdfExists: z.boolean(),
  moduleLink: z.string().nullable()
});

export const procedure_schema = z.object({
  procedureId: z.number(),
  annotation: z.string().nullable(),
  university: universities_schema,
  course: course_schema,
  createdAt: z.string(),
  status: z.enum(status_procedures.map((status) => status.match)),
  lastUpdated: z.string(),
  requestDetails: z.array(show_request_schema.omit({ procedureId: true }))
});

export const change_password_schema = z
  .object({
    password: z.string().min(1, { message: 'Passwort darf nicht leer sein.' }),
    confirm_password: z.string()
  })
  .refine((data) => data.password == data.confirm_password, {
    message: 'Nicht das selbe Passwort.',
    path: ['confirm_password']
  });

export const add_internal_modul_schema = z.object({
  moduleId: z.string(),
  number: z.string(),
  moduleName: z.string().min(1, { message: 'Name des Moduls darf nicht leer sein!' }),
  moduleDescription: z.string(),
  creditPoints: z.number().default(1),
  courseIds: z.array(z.string()).min(1, { message: 'Es muss mindestens ein Studiengang ausgewählt sein.' })
});
