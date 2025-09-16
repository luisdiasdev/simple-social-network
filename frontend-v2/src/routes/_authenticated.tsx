import { createFileRoute, Outlet, redirect } from '@tanstack/react-router';
import InternalTemplate from '../components/InternalLayout';

export const Route = createFileRoute('/_authenticated')({
  beforeLoad: ({ context }) => {
    const validCookie = typeof document !== 'undefined' && context.auth.hasValidAuthCookie();
    if (!context.auth.state.isAuthenticated && !validCookie) {
      // eslint-disable-next-line @typescript-eslint/only-throw-error
      throw redirect({
        to: '/',
        search: {
          redirect: typeof window !== 'undefined' ? window.location.pathname : '/',
        },
      });
    }
  },
  component: () => (
    <InternalTemplate>
      <Outlet />
    </InternalTemplate>
  ),
});
