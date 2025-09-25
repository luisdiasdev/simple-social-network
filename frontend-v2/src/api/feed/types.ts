import type { PostResponse } from '../posts/types';
import type { UserProfileResponse } from '../profile/types';

export type FeedResponse = {
  post: PostResponse;
  user: UserProfileResponse;
};
