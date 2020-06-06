import React from 'react';
import {
  Grid, makeStyles, CssBaseline, Paper, Box, Typography,
} from '@material-ui/core';
import HomeImg from '../../assets/images/home.jpg';
import Brand from '../../components/Brand';
import Form from './Form';

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
}));

export default function RegisterPage() {
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
            Sign up below! It only takes a few steps :)
          </Typography>
          <Form />
        </div>
      </Grid>
    </Grid>
  );
}
