import axios from 'axios';
import store from 'store';
import { logout } from 'store/ducks/auth';

const BASE_URL = 'http://localhost:8080';

export const publicApi = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

publicApi.interceptors.response.use(
  (response) => {
    if (response.status === 401) {
      store.dispatch(logout());
    }
    return response;
  },
);

export const isCancel = (value: any) => axios.isCancel(value);
