import z from 'zod';
import { user_roles } from '$lib/config';

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
