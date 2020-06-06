import React, { useState } from 'react';
import {
  makeStyles,
  Card,
  CardHeader,
  IconButton,
  CardMedia,
  CardContent,
  CardActions,
  Divider,
  ListItemText,
  ListItemIcon,
  MenuItem,
} from '@material-ui/core';
import {
  MoreVert as MoreVertIcon,
  Favorite as FavoriteIcon,
  Bookmark as BookmarkIcon,
  Share as ShareIcon,
  Delete as DeleteIcon,
} from '@material-ui/icons';
import { formatDistance } from 'date-fns';
import { UserProfileResponse } from 'api/profile/types';
import UserProfileAvatar from 'components/UserProfileAvatar';
import PostDisplay from 'components/Post/Display';
import { useDeletePost } from 'api/posts/hooks';
import { useSelector } from 'react-redux';
import { RootState } from 'store/ducks/root';
import PostMenu from './PostMenu';

const useStyles = makeStyles((theme) => ({
  paper: {
    margin: theme.spacing(2, 0),
  },
  image: {
    height: '100%',
  },
  expandIcon: {
    marginLeft: 'auto',
  },
}));

interface PostProps {
  id: number;
  title?: string;
  date: Date,
  avatarInitials?: string;
  avatarUrl?: string;
  message: Record<string, any>;
  pictures?: string[];
  profile?: UserProfileResponse;
  userId?: number;
}

export default function Post({
  id, profile, title, date, message, pictures, userId,
}: PostProps) {
  const classes = useStyles();
  const authUserId = useSelector((state: RootState) => state.auth.userId);
  const [deletePost] = useDeletePost();
  const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);

  const handleOptionsClick = (
    event: React.MouseEvent<HTMLElement>,
  ) => setAnchorEl(event.currentTarget);

  const handleOptionsClose = () => setAnchorEl(null);

  const enableDelete = userId === authUserId;
  const handleDeletePost = () => deletePost(id);

  return (
    <Card classes={{ root: classes.paper }} elevation={2}>
      <CardHeader
        avatar={(
          <UserProfileAvatar
            color={profile?.avatarColor || '#333'}
            profileImageUrl={profile?.imageUri}
            initials={profile?.initials}
          />
        )}
        action={(
          <IconButton aria-label="post-options" onClick={handleOptionsClick}>
            <MoreVertIcon />
          </IconButton>
        )}
        title={title}
        subheader={`${formatDistance(date, new Date())} ago`}
      />
      <PostMenu
        anchorEl={anchorEl}
        onClose={handleOptionsClose}
      >
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
      {(pictures && pictures.length) ? (
        <CardMedia
          component="img"
          className={classes.image}
          src={pictures[0]}
          title="Imagem"
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
