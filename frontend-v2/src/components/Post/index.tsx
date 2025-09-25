import type { UserProfileResponse } from '../../api/profile/types';

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

export default function Post(_props: PostProps) {
  return <div>Post</div>;
}
