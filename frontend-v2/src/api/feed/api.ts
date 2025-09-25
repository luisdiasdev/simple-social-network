import { publicApi } from '../client';
import type { Page } from '../types';
import type { FeedResponse } from './types';

const ENDPOINT = '/feed';

export const getFeed = (page: number = 0, pageSize: number = 10) =>
  publicApi.get<Page<FeedResponse>>(`${ENDPOINT}?page=${page}&size=${pageSize}`);
