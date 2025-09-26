import {
  Bookmark as BookmarkIcon,
  Delete as DeleteIcon,
  Favorite as FavoriteIcon,
  MoreVert as MoreVertIcon,
  Share as ShareIcon,
} from '@mui/icons-material';
import {
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  Divider,
  IconButton,
  ListItemIcon,
  ListItemText,
  MenuItem,
} from '@mui/material';
import { formatDistance } from 'date-fns/formatDistance';
import { useState } from 'react';
import { useDeletePost } from '../../api/posts';
import type { UserProfileResponse } from '../../api/profile/types';
import { useAuth } from '../../contexts/AuthContext';
import PostDisplay from '../Quill/Display';
import UserProfileAvatar from '../UserProfileAvatar';
import PostMenu from './Menu';

interface PostProps {
  id: number;
  title?: string;
  date: Date;
  avatarInitials?: string;
  avatarUrl?: string;
  // biome-ignore lint/suspicious/noExplicitAny: todo
  message: Record<string, any>;
  pictures?: string[];
  profile?: UserProfileResponse;
  userId?: number;
}

export default function Post({ id, profile, title, date, message, pictures, userId }: PostProps) {
  const { state } = useAuth();
  const authUserId = state.userId;
  const deletePost = useDeletePost();
  const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);

  const handleOptionsClick = (event: React.MouseEvent<HTMLElement>) => setAnchorEl(event.currentTarget);

  const handleOptionsClose = () => setAnchorEl(null);

  const enableDelete = userId === authUserId;
  const handleDeletePost = () => deletePost.mutate(id);

  return (
    <Card
      elevation={2}
      sx={{
        mt: 2,
        mb: 2,
      }}
    >
      <CardHeader
        avatar={
          <UserProfileAvatar
            color={profile?.avatarColor || '#333'}
            profileImageUrl={profile?.imageUri}
            initials={profile?.initials}
          />
        }
        action={
          <IconButton aria-label="post-options" onClick={handleOptionsClick}>
            <MoreVertIcon />
          </IconButton>
        }
        title={title}
        subheader={`${formatDistance(date, new Date())} ago`}
      />
      <PostMenu anchorEl={anchorEl} onClose={handleOptionsClose}>
        {enableDelete ? (
          <MenuItem onClick={handleDeletePost}>
            <ListItemIcon>
              <DeleteIcon />
            </ListItemIcon>
            <ListItemText primary="Remove" />
          </MenuItem>
        ) : null}
      </PostMenu>
      <Divider variant="fullWidth" />
      <CardContent>
        <PostDisplay postDelta={message} />
      </CardContent>
      {pictures?.length ? (
        <CardMedia
          component="img"
          sx={{
            height: '100%',
          }}
          src={pictures[0]}
          title={`post - ${id} image`}
        />
      ) : null}
      <CardActions disableSpacing>
        <IconButton aria-label="like">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="save">
          <BookmarkIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton>
      </CardActions>
    </Card>
  );
}
