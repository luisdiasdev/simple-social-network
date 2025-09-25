import type { UserProfileResponse } from '../../api/profile/types';

interface PostEditorProps {
  profile?: UserProfileResponse;
}

export default function PostEditor(_props: PostEditorProps) {
  return <div>Post Editor</div>;
}
