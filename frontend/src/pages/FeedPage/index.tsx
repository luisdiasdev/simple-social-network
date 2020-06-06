import React, { Fragment } from 'react';
import {
  Typography,
  Grid,
  makeStyles,
  CircularProgress,
} from '@material-ui/core';
import { parseISO } from 'date-fns';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useProfile } from 'api/profile/hooks';
import { useFeed } from 'api/feed/hooks';
import Post from './Post';
import PostEditor from './PostEditor';

const useStyles = makeStyles((theme) => ({
  paper: {
    margin: theme.spacing(2, 0),
  },
  icon: {
    color: 'rgba(255, 255, 255, 0.54)',
  },
  posts: {
    alignSelf: 'stretch',
    padding: theme.spacing(1),
  },
}));

export default function FeedPage() {
  const classes = useStyles();
  const { data: profile } = useProfile();
  const { data, fetchMore, canFetchMore } = useFeed();

  return (
    <div>
      <Grid
        container
        direction="row"
        justify="center"
        alignItems="center"
      >
        <Grid item lg={12}>
          <Typography variant="h5" component="h1" align="center">
            Welcome to the Feed!
          </Typography>
          <Typography variant="body1" component="p" align="center">
            Scroll down to see what other people are sharing or share your point of view!
          </Typography>
        </Grid>
        <Grid container justify="center">
          <Grid className={classes.posts} item xs={12} lg={6}>
            <PostEditor
              profile={profile}
            />
          </Grid>
        </Grid>
        <Grid className={classes.posts} item xs={12} lg={6}>
          <InfiniteScroll
            dataLength={data.length}
            next={fetchMore}
            hasMore={canFetchMore || false}
            loader={<CircularProgress />}
          >
            {data.map((page) => (
              <Fragment key={page.number}>
                {page?.content?.map((f) => (
                  <Post
                    key={f.post.id}
                    id={f.post.id}
                    title={f.user.displayName}
                    date={parseISO(f.post.createdAt)}
                    message={f.post.message}
                    pictures={f.post.pictures}
                    profile={f.user}
                    userId={f.post.userId}
                  />
                ))}
              </Fragment>
            ))}

          </InfiniteScroll>
        </Grid>
      </Grid>

    </div>
  );
}
