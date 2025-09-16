import { Avatar, useTheme } from '@mui/material';

interface UserProfileAvatarProps {
  profileImageUrl?: string;
  initials?: string;
  color: string;
}

export default function UserProfileAvatar({ color, initials, profileImageUrl }: UserProfileAvatarProps) {
  const theme = useTheme();

  if (profileImageUrl) {
    return <Avatar aria-label="user-profile-picture" alt={initials} src={profileImageUrl} />;
  }

  return (
    <Avatar
      aria-label="user-profile-initials"
      alt={initials}
      sx={{
        color: theme.palette.getContrastText(color),
        backgroundColor: color,
      }}
    >
      {initials || '-'}
    </Avatar>
  );
}
