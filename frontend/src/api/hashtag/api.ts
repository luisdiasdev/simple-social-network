import { publicApi } from 'client/http';
import debounce from 'lodash.debounce';
import { HashtagResponse } from './types';

const ENDPOINT = '/hashtags';

export const searchHashtag = (query: string) => publicApi.get<[HashtagResponse]>(
  `${ENDPOINT}?search=${query}`,
).then((response) => response.data);


export const searchHashtagDebounced = debounce(searchHashtag, 600, { leading: true });
