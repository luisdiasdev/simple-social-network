import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import {
  TextField,
  Button,
  FormControlLabel,
  Checkbox,
  Grid,
  Link,
  makeStyles,
} from '@material-ui/core';
import { useSnackbar } from 'notistack';
import Cookies from 'js-cookie';
import jwtDecode from 'jwt-decode';
import { useDispatch } from 'react-redux';
import { authenticationSuccess, authenticationFailed } from 'store/ducks/auth';
import * as AuthApi from 'api/auth';
import { LoginData } from 'api/auth/types';
import schema from './schema';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(2, 0, 2),
  },
  copyrightContainer: {
    position: 'fixed',
    bottom: theme.spacing(2),
  },
}));

export default function Form() {
  const classes = useStyles();
  const [submitting, setSubmitting] = useState(false);
  const { enqueueSnackbar } = useSnackbar();
  const dispatch = useDispatch();
  const { handleSubmit, register, errors } = useForm<LoginData>({
    validationSchema: schema,
  });

  const onSubmit = async (form: LoginData) => {
    setSubmitting(true);
    try {
      await AuthApi.login(form);
      const payloadCookie = Cookies.get('payload');

      if (payloadCookie) {
        const jwtPayload = jwtDecode<{ userId: number }>(payloadCookie);
        dispatch(authenticationSuccess({
          userId: jwtPayload.userId,
          username: form.username,
        }));
      }
    } catch (error) {
      enqueueSnackbar('Failed to complete your request', {
        variant: 'error',
      });
      dispatch(authenticationFailed());
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
        name="password"
        label="Password"
        type="password"
        autoComplete="current-password"
        inputRef={register}
        error={Boolean(errors.password)}
        helperText={errors?.password?.message}
        disabled={submitting}
      />
      <FormControlLabel
        control={<Checkbox value="remember" color="primary" disabled={submitting} />}
        label="Remember me"
      />
      <Button
        type="submit"
        fullWidth
        variant="contained"
        color="primary"
        className={classes.submit}
        disabled={submitting}
      >
        Sign In
      </Button>
      <Grid container>
        <Grid item xs>
          <Link href="!#" variant="body2">
            Forgot password?
          </Link>
        </Grid>
        <Grid item>
          <Link href="/signup" variant="body2">
            Don&apos;t have an account? Sign Up
          </Link>
        </Grid>
      </Grid>
    </form>
  );
}
