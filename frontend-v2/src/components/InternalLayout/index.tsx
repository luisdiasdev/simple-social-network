import { Dashboard as DashboardIcon } from '@mui/icons-material';
import {
  Box,
  CssBaseline,
  Divider,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { useState } from 'react';
import { useProfile } from '../../api/profile/hooks';
import AppToolbar from './AppToolbar';
import SideDrawer from './SideDrawer';

const Root = styled('div')({
  display: 'flex',
});

const Content = styled('main')(({ theme }) => ({
  flexGrow: 1,
  overflow: 'auto',
  margin: theme.spacing(4),
}));

const InternalTemplate: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [mobileOpen, setMobileOpen] = useState(false);
  const { data } = useProfile();

  const toggleMobileOpen = () => setMobileOpen(!mobileOpen);

  return (
    <Root>
      <CssBaseline />
      <AppToolbar profile={data} onMenuClick={toggleMobileOpen} />
      <SideDrawer open={mobileOpen} onClose={toggleMobileOpen}>
        <Toolbar />
        <Box sx={{ overflow: 'auto' }}>
          <List>
            <ListItem disablePadding>
              <ListItemButton>
                <ListItemIcon>
                  <DashboardIcon />
                </ListItemIcon>
                <ListItemText primary="Dashboard" />
              </ListItemButton>
            </ListItem>
          </List>
          <Divider />
        </Box>
      </SideDrawer>
      <Content>
        <Toolbar />
        {children}
      </Content>
    </Root>
  );
};

export default InternalTemplate;
