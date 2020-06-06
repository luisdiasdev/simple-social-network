import React, { ReactNode } from 'react';
import {
  useTheme, useMediaQuery, makeStyles,
} from '@material-ui/core';
import TemporaryDrawer from './TemporaryDrawer';
import PermanentDrawer from './PermanentDrawer';

interface SideDrawerProps {
    children: ReactNode
    open: boolean,
    onClose: () => void;
}

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  drawer: {
    [theme.breakpoints.up('md')]: {
      width: drawerWidth,
      flexShrink: 0,
    },
  },
}));

export default function SideDrawer({ children, open, onClose }: SideDrawerProps) {
  const classes = useStyles();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.up('md'));

  const Drawer = !isMobile ? TemporaryDrawer : PermanentDrawer;

  return (
    <nav className={classes.drawer}>
      <Drawer open={open} onClose={onClose}>
        {children}
      </Drawer>
    </nav>
  );
}
