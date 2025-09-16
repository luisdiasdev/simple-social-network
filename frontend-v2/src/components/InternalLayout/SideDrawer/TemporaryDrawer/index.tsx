import { Drawer } from '@mui/material';
import type { ReactNode } from 'react';
import { DRAWER_WIDTH } from '../constants';

interface TemporaryDrawerProps {
  children: ReactNode;
  open: boolean;
  onClose: () => void;
}

export default function TemporaryDrawer({ children, open, onClose }: TemporaryDrawerProps) {
  return (
    <Drawer
      anchor="left"
      variant="temporary"
      open={open}
      onClose={onClose}
      ModalProps={{
        keepMounted: true,
      }}
      sx={{
        width: DRAWER_WIDTH,
        flexShrink: 0,
        '& .MuiDrawer-paper': {
          width: DRAWER_WIDTH,
        },
      }}
    >
      {children}
    </Drawer>
  );
}
