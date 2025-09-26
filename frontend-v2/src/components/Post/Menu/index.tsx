import { Menu } from '@mui/material';
import type { ReactNode } from 'react';

interface PostMenuProps {
  anchorEl: HTMLElement | null;
  onClose: () => void;
  children: ReactNode;
}

function PostMenu({ anchorEl, onClose, children }: PostMenuProps) {
  return (
    <Menu
      anchorEl={anchorEl}
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
