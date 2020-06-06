import React from 'react';
import {
  CssBaseline, Paper, Grid, Typography, makeStyles, Box,
} from '@material-ui/core';
import HomeImg from '../../assets/images/home.jpg';
import Copyright from '../../components/Copyright';
import Form from './Form';
import Brand from '../../components/Brand';


const useStyles = makeStyles((theme) => ({
  root: {
    height: '100vh',
  },
  image: {
    backgroundImage: `url(${HomeImg})`,
    backgroundRepeat: 'no-repeat',
    backgroundColor:
      theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
    backgroundSize: 'cover',
    backgroundPosition: 'center',
  },
  paper: {
    margin: theme.spacing(8, 4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  brand: {
    margin: theme.spacing(4),
  },
  copyrightContainer: {
    margin: theme.spacing(3, 4),
    alignSelf: 'flex-end',
  },
}));

export default function LoginPage() {
  const classes = useStyles();

  return (
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={7} className={classes.image} />
      <Grid
        container
        item
        xs={12}
        sm={8}
        md={5}
        component={Paper}
        elevation={6}
        square
      >
        <div className={classes.paper}>
          <Box className={classes.brand}>
            <Brand />
          </Box>
          <Typography component="h1" variant="h6">
            Sign in
          </Typography>
          <Form />
        </div>
        <Grid item container justify="center">
          <Grid item className={classes.copyrightContainer}>
            <Copyright />
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
