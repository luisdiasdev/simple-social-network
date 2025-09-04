import { Box, CssBaseline, Grid, Paper, Typography } from '@mui/material';
import HomeImg from '../../assets/images/home.jpg';
import Brand from '../Brand';
import Copyright from '../Copyright';

type AuthLayoutProps = {
  title: string;
  form: React.ReactNode;
};

export default function AuthLayout(props: AuthLayoutProps) {
  return (
    <Grid container component="main" sx={{ height: '100vh' }}>
      <CssBaseline />
      <Grid
        size={{ xs: false, sm: 4, md: 7 }}
        sx={(theme) => ({
          backgroundImage: `url(${HomeImg})`,
          backgroundRepeat: 'no-repeat',
          backgroundColor: theme.palette.mode === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        })}
      />
      <Grid container size={{ xs: 12, sm: 8, md: 5 }} component={Paper} elevation={6} square>
        <Box
          sx={{
            m: [8, 4],
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            width: '100%',
          }}
        >
          <Box sx={{ m: 4 }}>
            <Brand />
          </Box>
          <Typography component="h1" variant="h6">
            {props.title}
          </Typography>
          {props.form}
        </Box>
        <Grid container sx={{ justifyContent: 'center', width: '100%' }}>
          <Grid sx={{ margin: [3, 4], alignSelf: 'flex-end' }}>
            <Copyright />
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
}
