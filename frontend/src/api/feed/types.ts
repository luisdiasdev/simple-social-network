import { PostResponse } from 'api/posts/types';
import { UserProfileResponse } from 'api/profile/types';

export type FeedResponse = {
  post: PostResponse;
  user: UserProfileResponse;
}
