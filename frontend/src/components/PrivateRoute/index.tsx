import React from 'react';
import { Route, RouteProps, Redirect } from 'react-router-dom';

export interface PrivateRouteProps extends RouteProps {
  isAuthenticated: boolean;
  redirectPath: string;
}

const PrivateRoute: React.FC<PrivateRouteProps> = (
  { isAuthenticated, redirectPath, ...rest }: PrivateRouteProps,
) => {
  if (isAuthenticated) {
    return <Route {...rest} />; // eslint-disable-line
  }
  return <Redirect to={redirectPath} />;
};

export default PrivateRoute;
