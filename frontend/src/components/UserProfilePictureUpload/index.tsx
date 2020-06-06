/* eslint-disable react/jsx-props-no-spreading */
import React, { useCallback } from 'react';
import { useDropzone } from 'react-dropzone';
import {
  Avatar, makeStyles, Tooltip, useTheme, Button, Grid,
} from '@material-ui/core';
import { useSnackbar } from 'notistack';
import { Close as CloseIcon } from '@material-ui/icons';

const useStyles = makeStyles((theme) => ({
  avatar: {
    height: theme.spacing(10),
    width: theme.spacing(10),
    cursor: 'pointer',
  },
  container: {
    alignItems: 'flex-start',
    flexFlow: 'column',
  },
  item: {
    margin: theme.spacing(1, 1, 0),
  },
}));

interface UserProfilePictureUploadProps {
  profileImageUrl?: string;
  initials?: string;
  color: string;
  onSelectImage: (file: File) => void;
  onDeleteImage?: () => void;
}

export default function UserProfileDropZone({
  color, initials, profileImageUrl, onSelectImage, onDeleteImage,
}: UserProfilePictureUploadProps) {
  const theme = useTheme();
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();

  const onDrop = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles.length > 1) {
      enqueueSnackbar('Please select only one image! =)', {
        variant: 'info',
      });
      return;
    }
    onSelectImage(acceptedFiles[0]);
  }, [enqueueSnackbar, onSelectImage]);

  const onDropRejected = useCallback(() => {
    enqueueSnackbar('Please select an image instead! =)', {
      variant: 'info',
    });
  }, [enqueueSnackbar]);

  const { getInputProps, getRootProps } = useDropzone({
    accept: 'image/*',
    onDropAccepted: onDrop,
    onDropRejected,
  });

  const input = (
    <input {...getInputProps()} />
  );

  const avatar = profileImageUrl ? (
    <Tooltip title="Upload your profile picture" arrow>
      <>
        {input}
        <Avatar
          aria-label="user-profile-picture-picker"
          src={profileImageUrl}
          alt={initials}
          className={classes.avatar}
          {...getRootProps()}
        />
      </>
    </Tooltip>
  ) : (
    <Tooltip title="Upload your profile picture" arrow {...getRootProps()}>
      <Avatar
        aria-label="user-profile-picture-picker"
        alt={initials}
        style={{
          color: theme.palette.getContrastText(color),
          backgroundColor: color,
        }}
        className={classes.avatar}
      >
        {input}
        {initials || '-'}
      </Avatar>
    </Tooltip>
  );

  return (
    <Grid className={classes.container} container>
      <Grid className={classes.item} item>
        {avatar}
      </Grid>
      {onDeleteImage && (
      <Grid className={classes.item} item>
        <Tooltip title="Delete your existing image" arrow>
          <Button
            fullWidth
            variant="text"
            color="primary"
            size="small"
            startIcon={(<CloseIcon />)}
            onClick={onDeleteImage}
          >
            Delete
          </Button>
        </Tooltip>
      </Grid>
      )}
    </Grid>
  );
}
