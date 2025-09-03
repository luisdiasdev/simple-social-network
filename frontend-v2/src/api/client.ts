import axios from 'axios'

const BASE_URL = '/api';

export const publicApi = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

publicApi.interceptors.response.use(
  (response) => {
    if (response.status === 401) {
        // TODO: Handle 401
    }
    return response;
  },
);

export const isCancel = (value: unknown) => axios.isCancel(value);