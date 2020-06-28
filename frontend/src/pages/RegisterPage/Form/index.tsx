import React, { useState } from 'react';
import { makeStyles, TextField, Button } from '@material-ui/core';
import { useForm } from 'react-hook-form';
import { useSnackbar } from 'notistack';
import { useHistory } from 'react-router-dom';
import schema, { RegisterFormData } from './schema';
import { publicApi } from '../../../client/http';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(2, 0, 2),
  },
}));

export default function Form() {
  const classes = useStyles();
  const history = useHistory();
  const [submitting, setSubmitting] = useState(false);
  const { enqueueSnackbar } = useSnackbar();

  const { handleSubmit, register, errors } = useForm<RegisterFormData>({
    validationSchema: schema,
  });

  const onSubmit = async (form: RegisterFormData) => {
    setSubmitting(true);
    try {
      await publicApi.post('/users', form);
      enqueueSnackbar('Congratulations! Now you\'re a part of Food Social!', {
        variant: 'success',
      });
      history.push('/');
    } catch (error) {
      enqueueSnackbar('Failed to complete your request', {
        variant: 'error',
      });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className={classes.root} onSubmit={handleSubmit(onSubmit)} noValidate>
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Username"
        name="username"
        autoComplete="username"
        autoFocus
        inputRef={register}
        error={Boolean(errors.username)}
        helperText={errors?.username?.message}
        disabled={submitting}
      />
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Email"
        name="email"
        autoComplete="email"
        inputRef={register}
        error={Boolean(errors.email)}
        helperText={errors?.email?.message}
        disabled={submitting}
      />
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        name="password"
        label="Password"
        type="password"
        autoComplete="current-password"
        inputRef={register}
        error={Boolean(errors.password)}
        helperText={errors?.password?.message}
        disabled={submitting}
      />
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        name="confirmPassword"
        label="Confirm Password"
        type="password"
        autoComplete="current-password"
        inputRef={register}
        error={Boolean(errors.confirmPassword)}
        helperText={errors?.confirmPassword?.message}
        disabled={submitting}
      />
      <Button
        type="submit"
        fullWidth
        variant="contained"
        color="secondary"
        className={classes.submit}
        disabled={submitting}
      >
        Sign Up
      </Button>
    </form>
  );
}
