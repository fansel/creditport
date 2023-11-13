import { env } from '$env/dynamic/public'


export const title = 'Anrechungsportal';

export const api_endpoint = env.PUBLIC_API_ENDPOINT || 'http://backend:8080/api';
