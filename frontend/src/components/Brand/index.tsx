import React from 'react';
import {
  Avatar, makeStyles, colors, Typography, Grid,
} from '@material-ui/core';

const { deepOrange } = colors;

const useStyles = makeStyles((theme) => ({
  avatar: {
    color: theme.palette.getContrastText(deepOrange[500]),
    backgroundColor: deepOrange[500],
    fontSize: '2.2em',
    margin: theme.spacing(1),
  },
}));

export default function Brand() {
  const classes = useStyles();

  return (
    <Grid container direction="column" alignItems="center">
      <Grid item>
        <Avatar className={classes.avatar}>C</Avatar>
      </Grid>
      <Grid item>
        <Typography component="h3" variant="h4">The Circle</Typography>
      </Grid>
    </Grid>
  );
}
