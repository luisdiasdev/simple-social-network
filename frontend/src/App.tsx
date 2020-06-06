import React from 'react';
import { MuiThemeProvider } from '@material-ui/core';
import { SnackbarProvider } from 'notistack';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/es/integration/react'; // eslint-disable-line import/no-unresolved
import { ReactQueryConfigProvider } from 'react-query';
import Router from './router';
import theme from './theme';
import store, { persistor } from './store';

const queryConfig = {
  refetchAllOnWindowFocus: false,
};

function App() {
  return (
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <MuiThemeProvider theme={theme}>
          <ReactQueryConfigProvider config={queryConfig}>
            <SnackbarProvider anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'right',
            }}
            >
              <Router />
            </SnackbarProvider>
          </ReactQueryConfigProvider>
        </MuiThemeProvider>
      </PersistGate>
    </Provider>
  );
}

export default App;
