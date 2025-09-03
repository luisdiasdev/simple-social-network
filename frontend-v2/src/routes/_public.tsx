import { createFileRoute, Outlet, redirect } from '@tanstack/react-router';

export const Route = createFileRoute('/_public')({
  beforeLoad: () => {
    // if authenticated, redirect to dashboard
    if (document.cookie.includes('payload')) {
      // eslint-disable-next-line @typescript-eslint/only-throw-error
      throw redirect({
        to: '/dashboard',
      });
    }
  },
  component: () => <Outlet />,
});
