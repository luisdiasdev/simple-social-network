import { publicApi } from './client';
import type { LoginData } from './types';

type LoginResponse = {
  token: string;
};

export const login = (form: LoginData) => {
  const formData = new FormData();
  formData.set('username', form.username);
  formData.set('password', form.password);
  return publicApi.post<LoginResponse>('/login', formData, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  });
};
