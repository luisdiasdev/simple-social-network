import React, { useEffect } from 'react';
import { useForm, Controller, OnSubmit } from 'react-hook-form';
import {
  TextField, makeStyles, Button,
} from '@material-ui/core';
import { Save as SaveIcon } from '@material-ui/icons';
import { UserProfileModifyRequest, UserProfileResponse } from 'api/profile/types';
import UserProfilePictureUpload from 'components/UserProfilePictureUpload';
import schema from './schema';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    padding: theme.spacing(2),
  },
  submit: {
    margin: theme.spacing(2, 0, 0),
  },
}));

interface FormProps {
  initialValues?: UserProfileResponse;
  onSubmit: OnSubmit<UserProfileModifyRequest>;
  onSelectImage: (file: File) => void;
  onDeleteImage?: () => void;
  submitting: boolean;
}

export default function Form({
  initialValues, onSubmit, onSelectImage, onDeleteImage, submitting,
} : FormProps) {
  const classes = useStyles();
  const {
    handleSubmit, errors, control, reset,
  } = useForm<UserProfileModifyRequest>({
    validationSchema: schema,
    defaultValues: initialValues,
  });

  useEffect(() => {
    if (initialValues && reset) {
      reset(initialValues);
    }
  }, [initialValues, reset]);

  return (
    <form className={classes.root} onSubmit={handleSubmit(onSubmit)}>
      <UserProfilePictureUpload
        initials={initialValues?.initials}
        color={initialValues?.avatarColor || '#333'}
        profileImageUrl={initialValues?.imageUri}
        onSelectImage={onSelectImage}
        onDeleteImage={onDeleteImage}
      />

      <Controller
        as={(
          <TextField
            variant="outlined"
            margin="normal"
            autoComplete="displayName"
            size="small"
            placeholder="How you want to be called?"
            fullWidth
            error={Boolean(errors.displayName)}
            helperText={errors?.displayName?.message}
            disabled={submitting}
          />
        )}
        label="Display Name"
        name="displayName"
        control={control}
        defaultValue={initialValues?.displayName || ''}
      />

      <Controller
        as={(
          <TextField
            variant="outlined"
            margin="normal"
            autoComplete="website"
            size="small"
            placeholder="Where can someone find you online?"
            fullWidth
            error={Boolean(errors.website)}
            helperText={errors?.website?.message}
            disabled={submitting}
          />
        )}
        label="Website"
        name="website"
        control={control}
        defaultValue={initialValues?.website || ''}
      />

      <Controller
        as={(
          <TextField
            variant="outlined"
            margin="normal"
            autoComplete="bio"
            size="small"
            placeholder="Who are you?"
            fullWidth
            multiline
            rows={3}
            error={Boolean(errors.bio)}
            helperText={errors?.bio?.message}
            disabled={submitting}
          />
        )}
        label="Bio"
        name="bio"
        control={control}
        defaultValue={initialValues?.bio || ''}
      />
      <Button
        className={classes.submit}
        type="submit"
        fullWidth
        variant="contained"
        color="secondary"
        disabled={submitting}
        startIcon={<SaveIcon />}
      >
        Update
      </Button>
    </form>
  );
}
