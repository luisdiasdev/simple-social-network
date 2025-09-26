import { Send as SendIcon } from '@mui/icons-material';
import { Card, CardActions, CardContent, CardHeader, ClickAwayListener, Divider, Fab } from '@mui/material';
import { useRef, useState } from 'react';
import type ReactQuill from 'react-quill-new';
import { type ImageWithPreview, savePost } from '../../api/posts';
import type { UserProfileResponse } from '../../api/profile/types';
import Editor from '../Quill/Editor';
import UserProfileAvatar from '../UserProfileAvatar';

interface PostEditorProps {
  profile?: UserProfileResponse;
}

export default function PostEditor({ profile }: PostEditorProps) {
  const [editorState, setEditorState] = useState('');
  const editorRef = useRef<ReactQuill>(null);
  const [toolbarVisible, setToolbarVisible] = useState(true);
  const [submitDisabled, setSubmitDisabled] = useState(true);
  const [images, _setImages] = useState<ImageWithPreview[]>([]);

  const showToolbar = () => setToolbarVisible(true);
  const hideToolbar = () => setToolbarVisible(false);

  const toggleSubmitDisabled = () => {
    if (editorRef?.current?.getEditor() && editorRef.current.getEditor().getLength() < 2) {
      setSubmitDisabled(true);
    } else {
      setSubmitDisabled(false);
    }
  };

  const handleEditorChange = (value: string) => {
    setEditorState(value);
    toggleSubmitDisabled();
  };

  const onSubmitPost = async () => {
    const data = await savePost({
      message: editorRef?.current?.unprivilegedEditor?.getContents(),
      pictures: images.map((i) => i.remoteUuid) as string[],
    });

    console.log(data);
  };

  return (
    <ClickAwayListener onClickAway={hideToolbar}>
      <Card elevation={2} sx={{ mt: 2, mb: 2, overflow: 'visible' }}>
        <CardHeader
          subheader="New post"
          avatar={
            <UserProfileAvatar
              color={profile?.avatarColor || '#333'}
              profileImageUrl={profile?.imageUri}
              initials={profile?.initials}
            />
          }
        />
        <Divider variant="fullWidth" />
        <CardContent>
          <Editor state={editorState} onStateChange={handleEditorChange} onFocus={showToolbar} ref={editorRef} />
          {/* <PhotoPreviewUpload
            onDeleteImage={deleteImageSelected}
            onSelect={handleImagesSelected}
            onReject={handleImageRejected}
            images={images}
          /> */}
        </CardContent>
        {toolbarVisible && (
          <>
            <Divider variant="fullWidth" />
            <CardActions disableSpacing>
              {/* <PhotoUpload
                onSelect={handleImagesSelected}
                onReject={handleImageRejected}
              /> */}
              <Fab
                aria-label="send post"
                color="secondary"
                variant="extended"
                size="medium"
                onClick={onSubmitPost}
                disabled={submitDisabled}
              >
                Post
                <SendIcon sx={{ ml: 1 }} />
              </Fab>
            </CardActions>
          </>
        )}
      </Card>
    </ClickAwayListener>
  );
}
