/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { Fab, makeStyles } from '@material-ui/core';
import { PhotoCamera as PhotoCameraIcon } from '@material-ui/icons';
import { useDefaultDropzone } from 'hooks/dropzone';

const useStyles = makeStyles((theme) => ({
  root: {
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(1),
  },
}));

interface PhotoUploadProps {
  onSelect: (file: File[]) => void;
  onReject?: (files: File[]) => void;
}

export default function PhotoUpload({ onSelect, onReject }: PhotoUploadProps) {
  const classes = useStyles();
  const { getInputProps, getRootProps } = useDefaultDropzone({
    onSelect,
    onReject,
  });

  return (
    <div className={classes.root} {...getRootProps()}>
      <input {...getInputProps()} />
      <Fab
        size="small"
        aria-label="add photo"
        color="primary"
      >
        <PhotoCameraIcon />
      </Fab>
    </div>
  );
}
