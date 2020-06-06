import React, { ReactNode } from 'react';
import { Drawer, makeStyles } from '@material-ui/core';

interface PermanentDrawerProps {
    children: ReactNode
}

const drawerWidth = 240;
const useStyles = makeStyles({
  drawerPaper: {
    width: drawerWidth,
  },
});

export default function PermanentDrawer({ children }: PermanentDrawerProps) {
  const classes = useStyles();

  return (
    <Drawer
      anchor="left"
      variant="permanent"
      open
      classes={{
        paper: classes.drawerPaper,
      }}
    >
      {children}
    </Drawer>
  );
}
