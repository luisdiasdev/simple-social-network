import { publicApi } from '../../client/http';
import { LoginData } from './types';

export const login = (form: LoginData) => {
  const formData = new FormData();
  formData.set('username', form.username);
  formData.set('password', form.password);
  return publicApi.post('/login', formData, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  });
};
