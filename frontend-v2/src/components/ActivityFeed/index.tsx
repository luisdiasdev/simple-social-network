import { Box, CircularProgress, Grid, Typography } from '@mui/material';
import { parseISO } from 'date-fns';
import { Fragment } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useFeed } from '../../api/feed/hooks';
import { useProfile } from '../../api/profile/hooks';
import Post from '../Post';
import PostEditor from '../PostEditor';

export default function FeedPage() {
  const { data: profile } = useProfile();
  const { data, fetchNextPage, hasNextPage } = useFeed();

  return (
    <Box>
      <Grid container direction="row" justifyContent="center" alignItems="center">
        <Grid size={12} direction="column">
          <Typography variant="h5" component="h1" align="center">
            Welcome to the Feed!
          </Typography>
          <Typography variant="body1" component="p" align="center">
            Scroll down to see what other people are sharing or share your point of view!
          </Typography>
        </Grid>
        <Grid
          container
          size={12}
          justifyContent="center"
          sx={{
            padding: 1,
          }}
        >
          <Grid size={{ xs: 12, lg: 6 }}>
            <PostEditor profile={profile} />
          </Grid>
        </Grid>
        <Grid
          size={{ xs: 12, lg: 6 }}
          sx={{
            alignSelf: 'stretch',
            padding: 1,
          }}
        >
          <InfiniteScroll
            dataLength={data?.pages?.reduce((total, page) => total + (page?.content?.length || 0), 0) || 0}
            next={fetchNextPage}
            hasMore={hasNextPage || false}
            loader={<CircularProgress />}
          >
            {data?.pages?.map((page) => (
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
    </Box>
  );
}
