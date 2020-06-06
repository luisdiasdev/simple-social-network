import React, { ReactNode } from 'react';
import { Menu } from '@material-ui/core';

interface PostMenuProps {
  anchorEl: HTMLElement | null;
  onClose: () => void;
  children: ReactNode;
}

function PostMenu({ anchorEl, onClose, children }: PostMenuProps) {
  return (
    <Menu
      anchorEl={anchorEl}
      getContentAnchorEl={null}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      keepMounted
      open={Boolean(anchorEl)}
      onClose={onClose}
    >
      {children}
    </Menu>
  );
}

export default PostMenu;
