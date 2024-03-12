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
  name: z.string().min(1),
  password: z.string().min(1),
  role: z.enum(Object.values(user_roles))
});
