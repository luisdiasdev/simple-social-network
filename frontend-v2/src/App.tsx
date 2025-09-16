import { ThemeProvider } from '@mui/material/styles';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { createRouter, RouterProvider } from '@tanstack/react-router';
import { SnackbarProvider } from 'notistack';
import { useMemo } from 'react';
import type { AuthContextType } from './contexts/AuthContext';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { routeTree } from './routeTree.gen';
import { theme } from './theme';

// Create a typed router factory so we can pass dynamic context from React
export type RouterContext = {
  auth: AuthContextType;
};

function makeRouter(context: RouterContext) {
  return createRouter({ routeTree, context });
}

declare module '@tanstack/react-router' {
  // eslint-disable-next-line @typescript-eslint/consistent-type-definitions
  interface Register {
    router: ReturnType<typeof makeRouter>;
  }
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 5 * 60 * 1000, // 5 minutes
      retry: 1,
      refetchOnWindowFocus: false,
    },
  },
});

function RouterWithAuth() {
  const authContext = useAuth();

  // Create the router once; it will hold a mutable context reference
  const router = useMemo(() => makeRouter({ auth: authContext }), [authContext]);

  // Always keep the router's context in sync with the latest auth state
  router.update({ context: { auth: authContext } });

  return <RouterProvider router={router} />;
}

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <LocalizationProvider dateAdapter={AdapterDateFns}>
          <SnackbarProvider
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'right',
            }}
          >
            <AuthProvider>
              <RouterWithAuth />
            </AuthProvider>
          </SnackbarProvider>
        </LocalizationProvider>
      </ThemeProvider>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}

export default App;
