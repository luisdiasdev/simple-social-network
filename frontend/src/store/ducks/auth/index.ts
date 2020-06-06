import { createSlice, PayloadAction } from '@reduxjs/toolkit';

type AuthUserData = {
  readonly userId?: number;
  readonly username?: string;
}

type AuthState = {
  readonly isAuthenticated: boolean;
} & AuthUserData

const initialState: AuthState = {
  isAuthenticated: false,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    authenticationSuccess(state, action: PayloadAction<AuthUserData>) {
      const { userId, username } = action.payload;
      state.userId = userId;
      state.username = username;
      state.isAuthenticated = true;
      return state;
    },
    authenticationFailed: (state) => {
      state.isAuthenticated = false;
      return state;
    },
    logout: (state) => {
      state = { ...initialState };
      state.isAuthenticated = false;
      return state;
    },
  },
});

export const { authenticationSuccess, authenticationFailed, logout } = authSlice.actions;

export default authSlice.reducer;
