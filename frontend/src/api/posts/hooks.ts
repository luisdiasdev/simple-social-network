import { useMutation, queryCache } from 'react-query';
import { deletePost } from './api';

export function useDeletePost() {
  return useMutation(
    (postId: number) => deletePost(postId),
    {
      onSuccess: () => queryCache.refetchQueries('feed'),
    },
  );
}
