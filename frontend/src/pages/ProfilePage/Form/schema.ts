import * as yup from 'yup';

const schema = yup.object().shape({
  displayName: yup.string().max(60),
  website: yup.string().max(100),
  bio: yup.string().max(200),
});

export default schema;
