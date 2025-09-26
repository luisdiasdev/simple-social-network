import debounce from 'debounce';
import { publicApi } from '../client';
import type { UserSearchResponse } from './types';

const ENDPOINT = '/users';

export const searchUsersByName = (query: string) =>
  publicApi.get<[UserSearchResponse]>(`${ENDPOINT}/name?search=${query}`).then((response) => response.data);

export const searchUsersByNameDebounced = debounce(searchUsersByName, 600);
