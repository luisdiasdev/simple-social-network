import { useMediaQuery, useTheme } from '@mui/material';
import type { ReactNode } from 'react';
import PermanentDrawer from './PermanentDrawer';
import TemporaryDrawer from './TemporaryDrawer';

interface SideDrawerProps {
  children: ReactNode;
  open: boolean;
  onClose: () => void;
}

export default function SideDrawer({ children, open, onClose }: SideDrawerProps) {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.up('md'));

  const Drawer = !isMobile ? TemporaryDrawer : PermanentDrawer;

  return (
    <Drawer open={open} onClose={onClose}>
      {children}
    </Drawer>
  );
}
