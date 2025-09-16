import { Menu as MenuIcon } from '@mui/icons-material';
import {
  AppBar,
  type AppBarProps,
  IconButton,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
  useMediaQuery,
  useScrollTrigger,
  useTheme,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { useNavigate } from '@tanstack/react-router';
import React, { type PropsWithChildren, useState } from 'react';
import type { UserProfileResponse } from '../../../api/profile/types';
import UserProfileAvatar from '../../UserProfileAvatar';

const ElevationScroll: React.FC<PropsWithChildren> = ({ children }) => {
  const trigger = useScrollTrigger({
    disableHysteresis: true,
    threshold: 0,
  });

  if (!React.isValidElement(children)) {
    return <>{children}</>;
  }

  return React.cloneElement(children as React.ReactElement<AppBarProps>, {
    elevation: trigger ? 4 : 0,
  });
};

const StyledAppBar = styled(AppBar)(({ theme }) => ({
  zIndex: theme.zIndex.drawer + 1,
}));

const Title = styled(Typography)({
  flexGrow: 1,
});

interface AppToolbarProps {
  onMenuClick: () => void;
  profile?: UserProfileResponse;
}

export default function AppToolbar({ profile, onMenuClick }: AppToolbarProps) {
  const theme = useTheme();
  const navigate = useNavigate();
  //   const dispatch = useDispatch();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const menuId = 'user-menu';

  const isSmallScreen = useMediaQuery(theme.breakpoints.down('md'));
  const isMenuOpen = Boolean(anchorEl);

  const handleUserMenuOpen = (event: React.MouseEvent<HTMLElement>) => setAnchorEl(event.currentTarget);

  const handleLogOut = () => {
    // dispatch(logout());
    navigate({ to: '/' });
  };

  const handleProfileOpen = () => navigate({ to: '/dashboard' });

  const handleMenuClose = () => setAnchorEl(null);

  const handleTitleClick = () => navigate({ to: '/' });

  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      id={menuId}
      keepMounted
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={isMenuOpen}
      onClose={handleMenuClose}
    >
      <MenuItem onClick={handleProfileOpen}>Profile</MenuItem>
      <MenuItem onClick={handleLogOut}>Log out</MenuItem>
    </Menu>
  );

  return (
    <ElevationScroll>
      <StyledAppBar position="fixed">
        <Toolbar>
          {isSmallScreen && (
            <IconButton color="inherit" aria-label="open sidebar" onClick={onMenuClick} size="large">
              <MenuIcon />
            </IconButton>
          )}
          <Title variant="h6" color="inherit" noWrap>
            <Typography
              component="span"
              color="inherit"
              sx={{
                cursor: 'pointer',
                textDecoration: 'none',
                '&:hover': {
                  textDecoration: 'underline',
                },
              }}
              onClick={handleTitleClick}
            >
              Food Social
            </Typography>
          </Title>
          <IconButton
            edge="end"
            aria-label="account of current user"
            aria-controls={menuId}
            aria-haspopup="true"
            onClick={handleUserMenuOpen}
            color="inherit"
            size="large"
          >
            <UserProfileAvatar
              color={profile?.avatarColor || '#333'}
              profileImageUrl={profile?.imageUri}
              initials={profile?.initials}
            />
          </IconButton>
        </Toolbar>
      </StyledAppBar>
      {renderMenu}
    </ElevationScroll>
  );
}
