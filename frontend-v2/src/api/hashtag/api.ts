import debounce from 'debounce';
import { publicApi } from '../client';
import type { HashtagResponse } from './types';

const ENDPOINT = '/hashtags';

export const searchHashtag = (query: string) =>
  publicApi.get<[HashtagResponse]>(`${ENDPOINT}?search=${query}`).then((response) => response.data);

export const searchHashtagDebounced = debounce(searchHashtag, 600, { immediate: true });
