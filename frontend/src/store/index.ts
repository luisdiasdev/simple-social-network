import {
  configureStore, ThunkAction, Action,
} from '@reduxjs/toolkit';
import { persistReducer, PersistConfig, persistStore } from 'redux-persist';
import localForage from 'localforage';
import autoMergeLevel2 from 'redux-persist/es/stateReconciler/autoMergeLevel2'; // eslint-disable-line import/no-unresolved
import thunk from 'redux-thunk';
import rootReducer, { RootState } from './ducks/root';

localForage.config({
  name: 'circle-app',
});

const persistConfig: PersistConfig<RootState> = {
  key: 'root',
  storage: localForage,
  stateReconciler: autoMergeLevel2,
};

const persistedReducer = persistReducer(persistConfig, rootReducer);


const store = configureStore({
  reducer: persistedReducer,
  middleware: [thunk],
});

export type AppDispatch = typeof store.dispatch;
export type AppThunk = ThunkAction<void, RootState, null, Action<string>>

export const persistor = persistStore(store);
export default store;
