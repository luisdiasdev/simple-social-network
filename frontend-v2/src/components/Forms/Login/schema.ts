import * as yup from 'yup';

const schema = yup.object().shape({
  username: yup.string().min(4).max(30).required(),
  password: yup.string().min(8).required(),
});

export default schema;
