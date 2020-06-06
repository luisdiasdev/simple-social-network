import { publicApi } from 'client/http';
import { Page } from 'api/types';
import { FeedResponse } from './types';

const ENDPOINT = '/feed';

export const getFeed = (page: number = 0, pageSize: number = 10) => publicApi
  .get<Page<FeedResponse>>(`${ENDPOINT}?page=${page}&size=${pageSize}`);
