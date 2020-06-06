import { publicApi } from 'client/http';
import debounce from 'lodash.debounce';
import { UserSearchResponse } from './types';

const ENDPOINT = '/users';

export const searchUsersByName = (query: string) => publicApi.get<[UserSearchResponse]>(
  `${ENDPOINT}/name?search=${query}`,
).then((response) => response.data);

export const searchUsersByNameDebounced = debounce(searchUsersByName, 600);
