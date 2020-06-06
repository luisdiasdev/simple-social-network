import { useInfiniteQuery } from 'react-query';
import { getFeed } from './api';

export const useFeed = () => useInfiniteQuery(
  'feed',
  async (key, nextPage: number = 0) => {
    const { data } = await getFeed(nextPage, 2);
    return data;
  },
  {
    getFetchMore: (last) => {
      const hasMore = last.number < last.totalPages;
      return hasMore && last.number + 1;
    },
  },
);
