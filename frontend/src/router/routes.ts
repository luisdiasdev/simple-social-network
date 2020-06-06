import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import FeedPage from '../pages/FeedPage';
import ProfilePage from '../pages/ProfilePage';


type Route = {
    path: string,
    exact: boolean,
    component: React.ComponentType<any>
}

export enum Paths {
  HOME = '/',
  SIGNUP = '/signup',
  FEED = '/feed',
  PROFILE = '/profile'
}

export const routes: Route[] = [{
  path: Paths.FEED,
  exact: true,
  component: FeedPage,
}, {
  path: Paths.PROFILE,
  exact: true,
  component: ProfilePage,
}];

export const publicRoutes: Route[] = [{
  path: Paths.HOME,
  exact: true,
  component: LoginPage,
},
{
  path: Paths.SIGNUP,
  exact: true,
  component: RegisterPage,
}];

export const PublicHomeRoute = Paths.HOME;
export const PrivateHomeRoute = Paths.FEED;
