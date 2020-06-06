import * as yup from 'yup';

export interface RegisterFormData {
  username: string;
  password: string;
  confirmPassword: string;
  email: string;
}

const schema = yup.object().shape({
  username: yup.string().min(4).max(30).required(),
  password: yup.string().min(8).required(),
  confirmPassword: yup.string()
    .min(8)
    .oneOf([yup.ref('password'), null], 'passwords doesn\'t match')
    .required(),
  email: yup.string().email().required(),
});

export default schema;
