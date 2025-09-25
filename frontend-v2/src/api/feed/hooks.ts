import { useInfiniteQuery } from '@tanstack/react-query';
import { getFeed } from './api';

export const useFeed = () =>
  useInfiniteQuery({
    queryKey: ['feed'],
    queryFn: async ({ pageParam = 0 }) => {
      const { data } = await getFeed(pageParam, 2);
      return data;
    },
    getNextPageParam: (lastPage) => {
      const hasMore = lastPage.number < lastPage.totalPages - 1;
      return hasMore ? lastPage.number + 1 : undefined;
    },
    initialPageParam: 0,
  });
