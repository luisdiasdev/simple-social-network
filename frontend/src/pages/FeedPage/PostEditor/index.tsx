import React, { useState, useRef, useEffect } from 'react';
import {
  Card, makeStyles, CardHeader, CardContent, Divider, CardActions, Fab, ClickAwayListener,
} from '@material-ui/core';
import { Send as SendIcon } from '@material-ui/icons';
import { v4 as uuidv4 } from 'uuid';
import { UserProfileResponse } from 'api/profile/types';
import UserProfileAvatar from 'components/UserProfileAvatar';
import Editor from 'components/Post/Editor';
import ReactQuill from 'react-quill';
import PhotoUpload from 'components/PhotoUpload';
import PhotoPreviewUpload from 'components/PhotoPreviewUpload';
import { useSnackbar } from 'notistack';
import { savePostPicture, savePost } from 'api/posts/api';
import { ImageWithPreview } from 'api/posts/types';
import { MAX_POST_FILES, MAX_FILE_SIZE } from 'constants/upload';

const useStyles = makeStyles((theme) => ({
  paper: {
    margin: theme.spacing(2, 0),
    overflow: 'visible',
  },
  image: {
    height: '100%',
  },
  expandIcon: {
    marginLeft: 'auto',
  },
  extendedIcon: {
    marginLeft: theme.spacing(1),
  },
}));

interface PostEditorProps {
  profile?: UserProfileResponse;
}

export default function PostEditor({ profile }: PostEditorProps) {
  const classes = useStyles();
  const [editorState, setEditorState] = useState('');
  const [submitDisabled, setSubmitDisabled] = useState(true);
  const [toolbarVisible, setToolbarVisible] = useState(false);
  const [images, setImages] = useState<ImageWithPreview[]>([]);
  const { enqueueSnackbar } = useSnackbar();
  const editorRef = useRef<ReactQuill>(null);

  const onSubmitPost = async () => {
    // console.log(editorRef?.current?.unprivilegedEditor?.getContents());
    // console.log(editorState);
    // console.log(images);

    const data = await savePost({
      message: editorRef?.current?.unprivilegedEditor?.getContents(),
      pictures: images.map((i) => i.remoteUuid) as string[],
    });
    // eslint-disable-next-line no-console
    console.log(data);
  };

  const showToolbar = () => setToolbarVisible(true);
  const hideToolbar = () => setToolbarVisible(false);

  const toggleSubmitDisabled = () => {
    if (editorRef
      && editorRef.current
      && editorRef.current.getEditor()
      && editorRef.current.getEditor().getLength() < 2) {
      setSubmitDisabled(true);
    } else {
      setSubmitDisabled(false);
    }
  };

  const handleEditorChange = (value: string) => {
    setEditorState(value);
    toggleSubmitDisabled();
  };

  const handleImagesSelected = (files: File[]) => {
    if (!files) {
      enqueueSnackbar('Please select an image :(', { variant: 'error' });
      return;
    }

    if (!files.length || files.length > MAX_POST_FILES) {
      enqueueSnackbar('Please select up to 5 images :)', { variant: 'error' });
      return;
    }

    const imagesWithPreview = files.map((f) => {
      const { cancellationSource, promise } = savePostPicture(f);
      const localUuid = uuidv4();
      return {
        localUuid,
        file: f,
        preview: URL.createObjectURL(f),
        cancellationSource,
        promise,
        hasError: false,
      };
    });
    setImages([...images, ...imagesWithPreview]);
  };

  const handleImageRejected = (files: File[]) => {
    files.forEach((f) => {
      if (f.size > MAX_FILE_SIZE) {
        enqueueSnackbar('Please select a file up to 1MB', { variant: 'error' });
      }
    });
  };

  const deleteImageSelected = (image: ImageWithPreview) => {
    setImages(images.filter((i) => i.preview !== image.preview));
    URL.revokeObjectURL(image.preview);
  };

  useEffect(() => {
    images.filter((i) => i.promise).forEach((i) => i.promise && i.promise.then((res) => {
      const imgIndex = images.findIndex((a) => a.localUuid && a.localUuid === i.localUuid);
      const imgsRemoved = images.splice(imgIndex, 1);

      if (imgsRemoved && imgsRemoved.length) {
        const img = imgsRemoved[0];
        URL.revokeObjectURL(img.preview);
        img.preview = res.contentUri;
        img.remoteUuid = res.uuid;
        img.promise = undefined;
      }
      const newImages = [...images];
      newImages.splice(imgIndex, 0, ...imgsRemoved);
      setImages(newImages);
    }).catch((err) => {
      const imgIndex = images.findIndex((a) => a.localUuid && a.localUuid === i.localUuid);
      const imgsRemoved = images.splice(imgIndex, 1);

      if (imgsRemoved && imgsRemoved.length) {
        const img = imgsRemoved[0];
        img.hasError = true;
        img.promise = undefined;
        img.error = err;
      }
      const newImages = [...images];
      newImages.splice(imgIndex, 0, ...imgsRemoved);
      setImages(newImages);
    }));
    return () => {
      images.forEach((i) => URL.revokeObjectURL(i.preview));
    };
  }, [images]);

  return (
    <ClickAwayListener onClickAway={hideToolbar}>
      <Card classes={{ root: classes.paper }} elevation={2}>
        <CardHeader
          subheader="New post"
          avatar={(
            <UserProfileAvatar
              color={profile?.avatarColor || '#333'}
              profileImageUrl={profile?.imageUri}
              initials={profile?.initials}
            />
        )}
        />
        <Divider variant="fullWidth" />
        <CardContent>
          <Editor
            state={editorState}
            onStateChange={handleEditorChange}
            onFocus={showToolbar}
            ref={editorRef}
          />
          <PhotoPreviewUpload
            onDeleteImage={deleteImageSelected}
            onSelect={handleImagesSelected}
            onReject={handleImageRejected}
            images={images}
          />
        </CardContent>
        {toolbarVisible && (
          <>
            <Divider variant="fullWidth" />
            <CardActions disableSpacing>
              <PhotoUpload
                onSelect={handleImagesSelected}
                onReject={handleImageRejected}
              />
              <Fab
                aria-label="send post"
                color="secondary"
                variant="extended"
                size="medium"
                onClick={onSubmitPost}
                disabled={submitDisabled}
              >
                Post
                <SendIcon className={classes.extendedIcon} />
              </Fab>
            </CardActions>
          </>
        )}
      </Card>
    </ClickAwayListener>
  );
}
