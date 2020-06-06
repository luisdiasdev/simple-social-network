import React, { ReactNode } from 'react';
import { Drawer, makeStyles } from '@material-ui/core';

interface TemporaryDrawerProps {
    children: ReactNode
    open: boolean,
    onClose: () => void;
}

const drawerWidth = 240;
const useStyles = makeStyles({
  drawerPaper: {
    width: drawerWidth,
  },
});

export default function TemporaryDrawer({ children, open, onClose }: TemporaryDrawerProps) {
  const classes = useStyles();

  return (
    <Drawer
      anchor="left"
      variant="temporary"
      open={open}
      onClose={onClose}
      classes={{
        paper: classes.drawerPaper,
      }}
      ModalProps={{
        keepMounted: true,
      }}
    >
      {children}
    </Drawer>
  );
}
