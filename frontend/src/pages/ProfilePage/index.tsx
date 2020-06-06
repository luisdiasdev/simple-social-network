import React from 'react';
import {
  Grid, makeStyles, Paper, Typography, Divider,
} from '@material-ui/core';
import {
  useProfile, useUpdateProfile, useUpdateProfilePicture, useDeleteProfilePicture,
} from 'api/profile/hooks';
import { UserProfileModifyRequest } from 'api/profile/types';
import Form from './Form';

const useStyles = makeStyles((theme) => ({
  profile: {
    width: '100%',
    margin: theme.spacing(1),
  },
  title: {
    padding: theme.spacing(1, 1, 0),
  },
  paper: {
    padding: theme.spacing(1),
  },
}));

export default function ProfilePage() {
  const classes = useStyles();
  const { data: profile } = useProfile();
  const [updateProfile, { status }] = useUpdateProfile();
  const [updateProfilePicture, { status: statusPicture }] = useUpdateProfilePicture();
  const [deleteProfilePicture] = useDeleteProfilePicture();
  const submitting = status === 'loading' || statusPicture === 'loading';

  const onSubmit = (form: UserProfileModifyRequest) => {
    updateProfile(form);
  };

  const onSelectImage = (file: File) => {
    updateProfilePicture(file);
  };

  const onDeleteImage = () => {
    deleteProfilePicture();
  };

  return (
    <Grid
      container
      direction="row"
      justify="center"
      alignItems="center"
    >
      <Grid className={classes.profile} item lg={4}>
        <Paper elevation={2}>
          <Typography className={classes.title} variant="h6" component="h1">
            Profile
          </Typography>
          <Divider variant="fullWidth" />
          <Form
            initialValues={profile}
            onSubmit={onSubmit}
            onSelectImage={onSelectImage}
            onDeleteImage={onDeleteImage}
            submitting={submitting}
          />
        </Paper>
      </Grid>
    </Grid>
  );
}
