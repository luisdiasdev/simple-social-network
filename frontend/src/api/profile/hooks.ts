import { useQuery, useMutation, queryCache } from 'react-query';
import {
  getProfile, updateProfile, updateProfilePicture, deleteProfilePicture,
} from './api';
import { UserProfileModifyRequest, UserProfilePictureResponse } from './types';

export const useProfile = () => useQuery('profile', getProfile);

export const useUpdateProfile = () => useMutation(
  (form: UserProfileModifyRequest) => updateProfile(form),
  {
    onSuccess: () => queryCache.refetchQueries('profile'),
  },
);

export const useUpdateProfilePicture = () => useMutation(
  (form: File) => updateProfilePicture(form),
  {
    onSuccess: (data: UserProfilePictureResponse) => queryCache.setQueryData(
      'profile',
      (old: UserProfilePictureResponse | undefined) => (
        old
          ? ({ ...old, contentUri: data.contentUri })
          : data
      ),
    ),
  },
);

export const useDeleteProfilePicture = () => useMutation(
  () => deleteProfilePicture(),
  {
    onSuccess: () => queryCache.setQueryData(
      'profile',
      () => ({ contentUri: null }),
    ),
  },
);
