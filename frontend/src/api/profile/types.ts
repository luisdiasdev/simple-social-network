export type UserProfileResponse = {
  displayName?: string;
  website?: string;
  bio?: string;
  avatarColor?: string;
  initials?: string;
  imageUri?: string;
}

export type UserProfileModifyRequest = {
  displayName?: string;
  website?: string;
  bio?: string;
}

export type UserProfilePictureResponse = {
  contentUri: string;
}
