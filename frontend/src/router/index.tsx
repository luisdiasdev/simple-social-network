import React from 'react';
import { BrowserRouter, Switch } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from 'store/ducks/root';
import {
  routes, publicRoutes, PublicHomeRoute, PrivateHomeRoute,
} from './routes';
import PublicRoute from '../components/PublicRoute';
import InternalTemplate from '../components/InternalTemplate';
import PrivateRoute from '../components/PrivateRoute';

const renderWithTemplate = (component: React.ComponentType<any>) => () => {
  const RouteComponent = component;
  return (
    <InternalTemplate>
      <RouteComponent />
    </InternalTemplate>
  );
};

const Router = () => {
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  console.log('is Auth: ', isAuthenticated);
  return (
    <BrowserRouter>
      <Switch>
        {publicRoutes.map((route) => (
          <PublicRoute
            isAuthenticated={isAuthenticated}
            key={route.path}
            exact={route.exact}
            path={route.path}
            component={route.component}
            redirectPath={PrivateHomeRoute}
          />
        ))}
        {routes.map((route) => (
          <PrivateRoute
            isAuthenticated={isAuthenticated}
            key={route.path}
            exact={route.exact}
            path={route.path}
            render={renderWithTemplate(route.component)}
            redirectPath={PublicHomeRoute}
          />
        ))}
      </Switch>
    </BrowserRouter>
  );
};
export default Router;
