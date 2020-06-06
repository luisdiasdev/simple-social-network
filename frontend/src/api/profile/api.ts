import { publicApi } from 'client/http';
import { UserProfileResponse, UserProfileModifyRequest, UserProfilePictureResponse } from './types';

const ENDPOINT = '/users/profile';
const PICTURE_ENDPOINT = `${ENDPOINT}/picture`;

export const getProfile = () => publicApi.get<UserProfileResponse>(ENDPOINT)
  .then((response) => response.data);

export const updateProfile = (form: UserProfileModifyRequest) => publicApi
  .post<UserProfileModifyRequest>(ENDPOINT, form)
  .then((response) => response.data);

export const updateProfilePicture = (file: File) => {
  const formData = new FormData();
  formData.set('file', file);

  return publicApi.post<UserProfilePictureResponse>(PICTURE_ENDPOINT, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  }).then((response) => response.data);
};

export const deleteProfilePicture = () => publicApi.delete(PICTURE_ENDPOINT);
