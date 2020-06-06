import React, { useState } from 'react';
import {
  makeStyles,
  CssBaseline,
  Divider,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Hidden,
} from '@material-ui/core';
import {
  Dashboard as DashboardIcon,
} from '@material-ui/icons';
import { useProfile } from 'api/profile/hooks';
import SideDrawer from './SideDrawer';
import AppToolbar from './AppToolbar';

const useStyles = makeStyles((theme) => ({
  appBarSpacer: theme.mixins.toolbar,
  root: {
    display: 'flex',
  },
  content: {
    flexGrow: 1,
    overflow: 'auto',
    margin: theme.spacing(4),
  },
}));

const InternalTemplate: React.FC<{}> = ({ children }: React.PropsWithChildren<{}>) => {
  const classes = useStyles();
  const [mobileOpen, setMobileOpen] = useState(false);
  const { data } = useProfile();

  const toggleMobileOpen = () => setMobileOpen(!mobileOpen);

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppToolbar profile={data} onMenuClick={toggleMobileOpen} />
      <SideDrawer open={mobileOpen} onClose={toggleMobileOpen}>
        <Hidden smDown implementation="css">
          <div className={classes.appBarSpacer} />
        </Hidden>
        <List>
          <ListItem button>
            <ListItemIcon>
              <DashboardIcon />
            </ListItemIcon>
            <ListItemText primary="Dashboard" />
          </ListItem>
        </List>
        <Divider />
      </SideDrawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        {children}
      </main>
    </div>
  );
};

export default InternalTemplate;
