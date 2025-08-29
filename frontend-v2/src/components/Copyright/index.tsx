import { Typography, Link } from '@mui/material';

export default function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="/#">
        lgdias
      </Link>{' '}
      {new Date().getFullYear()}.
    </Typography>
  );
}
