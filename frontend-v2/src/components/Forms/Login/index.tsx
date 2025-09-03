import { useSnackbar } from 'notistack';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import type { LoginData } from '../../../api/types';
import { Box, Grid, Button, Checkbox, FormControlLabel, Link, TextField } from '@mui/material';
import { AuthApi } from '../../../api';

export default function LoginForm() {
  const [submitting, setSubmitting] = useState(false);
  const { enqueueSnackbar } = useSnackbar();
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm<LoginData>();

  const onSubmit = async (data: LoginData) => {
    setSubmitting(true);
    try {
      const response = await AuthApi.login(data)
      document.cookie = `payload=${response.data.token}`;
      enqueueSnackbar('Login successful', { variant: 'success' });
    } catch (error: unknown) {
      console.log(error)
      enqueueSnackbar('Login failed', { variant: 'error' });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit(onSubmit)}
      noValidate
      sx={{ mt: 1, width: '100%' }}
    >
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Username"
        autoComplete="username"
        {...register('username')} // Updated register syntax
        error={Boolean(errors.username)}
        helperText={errors.username?.message}
        disabled={submitting}
      />
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Password"
        type="password"
        autoComplete="current-password"
        {...register('password')} // Updated register syntax
        error={Boolean(errors.password)}
        helperText={errors.password?.message}
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
        disabled={submitting}
        sx={{
          my: 2,
        }}
      >
        Sign In
      </Button>
      <Grid container spacing={2}>
        <Grid size="grow">
          <Link href="!#" variant="body2">
            Forgot password?
          </Link>
        </Grid>
        <Grid size="auto">
          <Link href="/signup" variant="body2">
            Don&apos;t have an account? Sign Up
          </Link>
        </Grid>
      </Grid>
    </Box>
  );
}
