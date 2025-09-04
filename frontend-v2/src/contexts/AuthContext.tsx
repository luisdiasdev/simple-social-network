import { createContext, useCallback, useContext, useEffect, useReducer, type ReactNode } from 'react';
import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode';

export type AuthState = {
  userId: number | null;
  username: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  initialized: boolean;
};

export type AuthContextType = {
  state: AuthState;
  dispatch: React.Dispatch<AuthAction>;
  hasValidAuthCookie: () => boolean;
};

type AuthAction =
  | { type: 'AUTH_START' }
  | { type: 'AUTH_SUCCESS'; payload: { userId: number; username: string } }
  | { type: 'AUTH_FAILED' }
  | { type: 'AUTH_RESTORE'; payload: { userId: number; username?: string } }
  | { type: 'AUTH_INITIALIZE_COMPLETE' }
  | { type: 'LOGOUT' };

const initialState: AuthState = {
  isAuthenticated: false,
  userId: null,
  username: null,
  loading: true,
  initialized: false,
};

function hasValidAuthCookie(): boolean {
  try {
    const token = Cookies.get('payload');
    if (!token) return false;
    const { exp } = jwtDecode<{ exp?: number }>(token);
    if (!exp) return true; // no exp means treat as valid
    return exp * 1000 > Date.now();
  } catch {
    return false;
  }
}

function authReducer(state: AuthState, action: AuthAction): AuthState {
  switch (action.type) {
    case 'AUTH_START':
      return { ...state, loading: true };
    case 'AUTH_SUCCESS':
      return {
        ...state,
        isAuthenticated: true,
        userId: action.payload.userId,
        username: action.payload.username,
        loading: false,
        initialized: true,
      };
    case 'AUTH_FAILED':
      return {
        ...state,
        isAuthenticated: false,
        userId: null,
        username: null,
        loading: false,
        initialized: true,
      };
    case 'AUTH_RESTORE':
      return {
        ...state,
        isAuthenticated: true,
        userId: action.payload.userId,
        username: action.payload.username ?? null,
        loading: false,
        initialized: true,
      };
    case 'AUTH_INITIALIZE_COMPLETE':
      return {
        ...state,
        loading: false,
        initialized: true,
      };
    case 'LOGOUT':
      // Clear cookies on logout
      Cookies.remove('payload');
      return {
        ...initialState,
        loading: false,
        initialized: true,
      };
    default:
      return state;
  }
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(authReducer, initialState);
  const hasValidAuthCookieFn = useCallback(hasValidAuthCookie, []);

  useEffect(() => {
    const initializeAuth = () => {
      try {
        const payloadCookie = Cookies.get('payload');

        if (payloadCookie) {
          // Decode the JWT to get user info
          const jwtPayload = jwtDecode<{
            userId: number;
            username?: string;
            exp?: number; // Expiration time
          }>(payloadCookie);

          // Check if token is expired
          if (jwtPayload.exp && jwtPayload.exp * 1000 < Date.now()) {
            // Token is expired, remove it
            Cookies.remove('payload');
            dispatch({ type: 'AUTH_INITIALIZE_COMPLETE' });
            return;
          }

          // Token is valid, restore auth state
          dispatch({
            type: 'AUTH_RESTORE',
            payload: {
              userId: jwtPayload.userId,
              username: jwtPayload.username,
            },
          });
        } else {
          // No token found
          dispatch({ type: 'AUTH_INITIALIZE_COMPLETE' });
        }
      } catch (error) {
        // Invalid token, remove it
        console.warn('Invalid JWT token found, removing:', error);
        Cookies.remove('payload');
        dispatch({ type: 'AUTH_INITIALIZE_COMPLETE' });
      }
    };

    initializeAuth();
  }, []);
  return (
    <AuthContext.Provider value={{ state, dispatch, hasValidAuthCookie: hasValidAuthCookieFn }}>
      {children}
    </AuthContext.Provider>
  );
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
