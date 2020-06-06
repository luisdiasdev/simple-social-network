import React from 'react';
import { Route, RouteProps, Redirect } from 'react-router-dom';

export interface PublicRouteProps extends RouteProps {
  isAuthenticated: boolean;
  redirectPath: string;
}

const PublicRoute: React.FC<PublicRouteProps> = (
  { isAuthenticated, redirectPath, ...rest }: PublicRouteProps,
) => {
  if (!isAuthenticated) {
    return <Route {...rest} />; // eslint-disable-line
  }
  return <Redirect to={redirectPath} />;
};

export default PublicRoute;
