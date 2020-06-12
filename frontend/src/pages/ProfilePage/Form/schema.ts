import * as yup from 'yup';

const schema = yup.object().shape({
  displayName: yup.string().max(60),
  website: yup.string().max(100).nullable(),
  bio: yup.string().max(200).nullable(),
});

export default schema;
