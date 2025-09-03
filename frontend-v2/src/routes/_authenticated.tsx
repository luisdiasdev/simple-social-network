import { createFileRoute, Outlet, redirect } from '@tanstack/react-router';

export const Route = createFileRoute('/_authenticated')({
  beforeLoad: () => {
    // if not authenticated, redirect to login
    if (!document.cookie.includes('payload')) {
      // eslint-disable-next-line @typescript-eslint/only-throw-error
      throw redirect({
        to: '/',
        search: {
          redirect: window.location.pathname,
        }
      });
    }
  },
  component: () => <Outlet />,
})