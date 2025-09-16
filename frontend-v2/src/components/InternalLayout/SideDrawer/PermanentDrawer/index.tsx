import { Drawer } from '@mui/material';
import type { ReactNode } from 'react';
import { DRAWER_WIDTH } from '../constants';

interface PermanentDrawerProps {
  children: ReactNode;
}

export default function PermanentDrawer({ children }: PermanentDrawerProps) {
  return (
    <Drawer
      anchor="left"
      variant="permanent"
      open
      sx={{
        width: DRAWER_WIDTH,
        flexShrink: 0,
        '& .MuiDrawer-paper': { width: DRAWER_WIDTH, boxSizing: 'border-box' },
      }}
    >
      {children}
    </Drawer>
  );
}
