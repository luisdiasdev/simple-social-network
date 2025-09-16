import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { deleteProfilePicture, getProfile, updateProfile, updateProfilePicture } from './api';
import type { UserProfileModifyRequest, UserProfilePictureResponse } from './types';

export const useProfile = () =>
  useQuery({
    queryKey: ['profile'],
    queryFn: getProfile,
  });

export const useUpdateProfile = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (form: UserProfileModifyRequest) => updateProfile(form),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['profile'] });
    },
  });
};

export const useUpdateProfilePicture = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (form: File) => updateProfilePicture(form),
    onSuccess: (data: UserProfilePictureResponse) => {
      queryClient.setQueryData(['profile'], (old: UserProfilePictureResponse | undefined) =>
        old ? { ...old, contentUri: data.contentUri } : data,
      );
    },
  });
};

export const useDeleteProfilePicture = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: () => deleteProfilePicture(),
    onSuccess: () => {
      queryClient.setQueryData(['profile'], () => ({ contentUri: null }));
    },
  });
};
