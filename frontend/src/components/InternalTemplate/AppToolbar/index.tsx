import React, { PropsWithChildren, useState } from 'react';
import {
  useScrollTrigger,
  makeStyles,
  AppBar,
  Hidden,
  IconButton,
  Typography,
  Toolbar,
  Menu,
  MenuItem,
  Link,
} from '@material-ui/core';
import { Menu as MenuIcon } from '@material-ui/icons';
import { useHistory } from 'react-router-dom';
import { UserProfileResponse } from 'api/profile/types';
import { useDispatch } from 'react-redux';
import { Paths } from 'router/routes';
import UserProfileAvatar from 'components/UserProfileAvatar';
import { logout } from 'store/ducks/auth';

const ElevationScroll: React.FC<{}> = ({ children }: PropsWithChildren<{}>) => {
  const trigger = useScrollTrigger({
    disableHysteresis: true,
    threshold: 0,
  });

  return React.cloneElement(children as React.ReactElement<any>, {
    elevation: trigger ? 4 : 0,
  });
};

const useStyles = makeStyles((theme) => ({
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  title: {
    flexGrow: 1,
  },
}));

interface AppToolbarProps {
  onMenuClick: () => void;
  profile?: UserProfileResponse;
}

export default function AppToolbar({ profile, onMenuClick }: AppToolbarProps) {
  const classes = useStyles();
  const history = useHistory();
  const dispatch = useDispatch();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const menuId = 'user-menu';

  const isMenuOpen = Boolean(anchorEl);

  const handleUserMenuOpen = (
    event: React.MouseEvent<HTMLElement>,
  ) => setAnchorEl(event.currentTarget);

  const handleLogOut = () => {
    dispatch(logout());
    history.push(Paths.HOME);
  };

  const handleProfileOpen = () => history.push(Paths.PROFILE);

  const handleMenuClose = () => setAnchorEl(null);

  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      getContentAnchorEl={null}
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
      <>
        <AppBar className={classes.appBar} position="fixed">
          <Toolbar>
            <Hidden mdUp implementation="css">
              <IconButton
                color="inherit"
                aria-label="open sidebar"
                onClick={onMenuClick}
              >
                <MenuIcon />
              </IconButton>
            </Hidden>
            <Typography
              component="h1"
              variant="h6"
              color="inherit"
              noWrap
              className={classes.title}
            >
              <Link href={Paths.FEED} color="inherit" underline="none">
                Food Social
              </Link>
            </Typography>
            <IconButton
              edge="end"
              aria-label="account of current user"
              aria-controls={menuId}
              aria-haspopup="true"
              onClick={handleUserMenuOpen}
              color="inherit"
            >
              <UserProfileAvatar
                color={profile?.avatarColor || '#333'}
                profileImageUrl={profile?.imageUri}
                initials={profile?.initials}
              />
            </IconButton>
          </Toolbar>
        </AppBar>
        {renderMenu}
      </>
    </ElevationScroll>
  );
}
