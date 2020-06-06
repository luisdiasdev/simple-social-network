/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { makeStyles, IconButton, Tooltip } from '@material-ui/core';
import {
  Close as CloseIcon,
  Add as AddIcon,
  Error as ErrorIcon,
} from '@material-ui/icons';
import { ImageWithPreview } from 'api/posts/types';
import { useDefaultDropzone } from 'hooks/dropzone';

const useStyles = makeStyles((theme) => ({
  thumbsContainer: {
    display: 'flex',
    flexDirection: 'row',
    flexWrap: 'wrap',
    marginTop: 16,
  },
  thumb: {
    display: 'inline-flex',
    borderRadius: 2,
    border: '1px solid #eaeaea',
    marginBottom: 8,
    marginRight: 8,
    width: 100,
    height: 100,
    boxSizing: 'border-box',
    opacity: '1',
    '&:hover': {
      opacity: '.5',
    },
  },
  thumbInner: {
    position: 'relative',
    display: 'flex',
    minWidth: 0,
    overflow: 'hidden',
  },
  plus: {
    border: '3px dashed #eaeaea',
    width: 100,
    height: 100,
    color: '#eaeaea',
    display: 'flex',
    justifyContent: 'center',
    cursor: 'pointer',
  },
  plusIcon: {
    alignSelf: 'center',
    fontSize: theme.spacing(6),
  },
  removeImgButton: {
    color: '#9292ff',
    position: 'absolute',
    right: '5px',
    top: '5px',
  },
  errorIconButton: {
    color: '#ff0000',
    position: 'absolute',
    top: '5px',
    left: '5px',
  },
  icon: {
    borderRadius: '50%',
    background: '#fff',
  },
}));

interface PhotoPreviewUploadProps {
  images: ImageWithPreview[];
  onDeleteImage: (image: ImageWithPreview) => void;
  onSelect: (file: File[]) => void;
  onReject?: (files: File[]) => void;
}

const PhotoPreviewUpload: React.FC<PhotoPreviewUploadProps> = ({
  images, onDeleteImage, onSelect, onReject,
}: PhotoPreviewUploadProps) => {
  const classes = useStyles();
  const { getInputProps, getRootProps } = useDefaultDropzone({
    onSelect,
    onReject,
  });

  const handleDeleteImage = (image: ImageWithPreview) => () => {
    if (onDeleteImage) {
      onDeleteImage(image);
    }
  };

  const thumbs = images.map((image) => (
    <div className={classes.thumb} key={image.localUuid}>
      <div className={classes.thumbInner}>
        <img
          src={image.preview}
          alt="post"
          width="100"
          height="100"
        />
        <IconButton
          aria-label="remove photo"
          className={classes.removeImgButton}
          size="small"
          onClick={handleDeleteImage(image)}
        >
          <CloseIcon className={classes.icon} fontSize="small" />
        </IconButton>
        {image.hasError && (
        <Tooltip title="There was an error while uploading your photo" arrow placement="top">
          <IconButton
            aria-label="info about photo"
            className={classes.errorIconButton}
            size="small"
          >
            <ErrorIcon className={classes.icon} fontSize="small" />
          </IconButton>
        </Tooltip>
        )}
      </div>
    </div>
  ));

  if (thumbs.length) {
    thumbs.push((
      <div className={classes.plus} key="plus" {...getRootProps()}>
        <AddIcon className={classes.plusIcon} />
        <input {...getInputProps()} />
      </div>
    ));
  }

  return (
    <section className={classes.thumbsContainer}>
      <aside className={classes.thumbsContainer}>
        {thumbs}
      </aside>
    </section>
  );
};

export default PhotoPreviewUpload;
