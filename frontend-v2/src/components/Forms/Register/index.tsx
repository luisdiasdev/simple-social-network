import { useSnackbar } from 'notistack';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField } from '@mui/material';
import type { RegisterFormData } from './schema';
import { publicApi } from '../../../api';
import { useNavigate } from '@tanstack/react-router';

export default function RegisterForm() {
  const [submitting, setSubmitting] = useState(false);
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm<RegisterFormData>();

  const onSubmit = async (form: RegisterFormData) => {
    setSubmitting(true);
    try {
      await publicApi.post('/users', form);

      enqueueSnackbar("Congratulations! Now you're a part of Food Social!", {
        variant: 'success',
      });
      await navigate({ to: '/' });
    } catch (error: unknown) {
      console.error(error);
      enqueueSnackbar('Registration failed', { variant: 'error' });
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
        label="Email"
        autoComplete="email"
        {...register('email')} // Updated register syntax
        error={Boolean(errors.email)}
        helperText={errors.email?.message}
        disabled={submitting}
      />
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Password"
        type="password"
        autoComplete="new-password"
        {...register('password')} // Updated register syntax
        error={Boolean(errors.password)}
        helperText={errors.password?.message}
        disabled={submitting}
      />
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Confirm Password"
        type="password"
        autoComplete="new-password"
        {...register('confirmPassword')} // Updated register syntax
        error={Boolean(errors.confirmPassword)}
        helperText={errors.confirmPassword?.message}
        disabled={submitting}
      />
      <Button
        type="submit"
        fullWidth
        variant="contained"
        color="secondary"
        disabled={submitting}
        sx={{
          my: 2,
        }}
      >
        Sign Up
      </Button>
    </Box>
  );
}
