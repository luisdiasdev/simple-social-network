import { createFileRoute, Outlet, redirect } from '@tanstack/react-router';

export const Route = createFileRoute('/_public')({
  beforeLoad: ({ context }) => {
    // if authenticated, redirect to dashboard
    const validCookie = typeof document !== 'undefined' && context.auth.hasValidAuthCookie();
    if (context.auth.state.isAuthenticated || validCookie) {
      // eslint-disable-next-line @typescript-eslint/only-throw-error
      throw redirect({
        to: '/dashboard',
      });
    }
  },
  component: () => <Outlet />,
});
