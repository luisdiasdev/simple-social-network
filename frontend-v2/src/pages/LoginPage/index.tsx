import { Box, Stack, Typography, CssBaseline, Card as MuiCard, Button, Checkbox, Divider, FormLabel, FormControl, FormControlLabel, Link, TextField, styled, SvgIcon } from '@mui/material';
import AutoFixHighRoundedIcon from '@mui/icons-material/AutoFixHighRounded';
import ConstructionRoundedIcon from '@mui/icons-material/ConstructionRounded';
import SettingsSuggestRoundedIcon from '@mui/icons-material/SettingsSuggestRounded';
import ThumbUpAltRoundedIcon from '@mui/icons-material/ThumbUpAltRounded';

/*
* Template take from: https://github.com/mui/material-ui/tree/v7.3.1/docs/data/material/getting-started/templates/sign-in-side
*/

export function FacebookIcon() {
  return (
    <SvgIcon>
      <svg
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M6.68 15.92C2.88 15.24 0 11.96 0 8C0 3.6 3.6 0 8 0C12.4 0 16 3.6 16 8C16 11.96 13.12 15.24 9.32 15.92L8.88 15.56H7.12L6.68 15.92Z"
          fill="url(#paint0_linear_795_116)"
        />
        <path
          d="M11.12 10.2391L11.48 7.99914H9.36V6.43914C9.36 5.79914 9.6 5.31914 10.56 5.31914H11.6V3.27914C11.04 3.19914 10.4 3.11914 9.84 3.11914C8 3.11914 6.72 4.23914 6.72 6.23914V7.99914H4.72V10.2391H6.72V15.8791C7.16 15.9591 7.6 15.9991 8.04 15.9991C8.48 15.9991 8.92 15.9591 9.36 15.8791V10.2391H11.12Z"
          fill="white"
        />
        <defs>
          <linearGradient
            id="paint0_linear_795_116"
            x1="8"
            y1="0"
            x2="8"
            y2="15.9991"
            gradientUnits="userSpaceOnUse"
          >
            <stop stopColor="#1AAFFF" />
            <stop offset="1" stopColor="#0163E0" />
          </linearGradient>
        </defs>
      </svg>
    </SvgIcon>
  );
}

export function GoogleIcon() {
  return (
    <SvgIcon>
      <svg
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M15.68 8.18182C15.68 7.61455 15.6291 7.06909 15.5345 6.54545H8V9.64364H12.3055C12.1164 10.64 11.5491 11.4836 10.6982 12.0509V14.0655H13.2945C14.8073 12.6691 15.68 10.6182 15.68 8.18182Z"
          fill="#4285F4"
        />
        <path
          d="M8 16C10.16 16 11.9709 15.2873 13.2945 14.0655L10.6982 12.0509C9.98545 12.5309 9.07636 12.8218 8 12.8218C5.92 12.8218 4.15273 11.4182 3.52 9.52727H0.858182V11.5927C2.17455 14.2036 4.87273 16 8 16Z"
          fill="#34A853"
        />
        <path
          d="M3.52 9.52C3.36 9.04 3.26545 8.53091 3.26545 8C3.26545 7.46909 3.36 6.96 3.52 6.48V4.41455H0.858182C0.312727 5.49091 0 6.70545 0 8C0 9.29455 0.312727 10.5091 0.858182 11.5855L2.93091 9.97091L3.52 9.52Z"
          fill="#FBBC05"
        />
        <path
          d="M8 3.18545C9.17818 3.18545 10.2255 3.59273 11.0618 4.37818L13.3527 2.08727C11.9636 0.792727 10.16 0 8 0C4.87273 0 2.17455 1.79636 0.858182 4.41455L3.52 6.48C4.15273 4.58909 5.92 3.18545 8 3.18545Z"
          fill="#EA4335"
        />
      </svg>
    </SvgIcon>
  );
}

const items = [
    {
        icon: <SettingsSuggestRoundedIcon sx={{ color: 'text.secondary' }} />,
        title: 'Adaptable performance',
        description:
            'Our product effortlessly adjusts to your needs, boosting efficiency and simplifying your tasks.',
    },
    {
        icon: <ConstructionRoundedIcon sx={{ color: 'text.secondary' }} />,
        title: 'Built to last',
        description:
            'Experience unmatched durability that goes above and beyond with lasting investment.',
    },
    {
        icon: <ThumbUpAltRoundedIcon sx={{ color: 'text.secondary' }} />,
        title: 'Great user experience',
        description:
            'Integrate our product into your routine with an intuitive and easy-to-use interface.',
    },
    {
        icon: <AutoFixHighRoundedIcon sx={{ color: 'text.secondary' }} />,
        title: 'Innovative functionality',
        description:
            'Stay ahead with features that set new standards, addressing your evolving needs better than the rest.',
    },
];

const Card = styled(MuiCard)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  alignSelf: 'center',
  width: '100%',
  padding: theme.spacing(4),
  gap: theme.spacing(2),
  boxShadow:
    'hsla(220, 30%, 5%, 0.05) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.05) 0px 15px 35px -5px',
  [theme.breakpoints.up('sm')]: {
    width: '450px',
  },
  ...theme.applyStyles('dark', {
    boxShadow:
      'hsla(220, 30%, 5%, 0.5) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.08) 0px 15px 35px -5px',
  }),
}));


export function LoginPage(props: { disableCustomTheme?: boolean }) {
    const emailError = false;
    const passwordError = false;
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(event);
    };

    return (
        <>
            <CssBaseline enableColorScheme />
            {/* <ColorModeSelect sx={{ position: 'fixed', top: '1rem', right: '1rem' }} /> */}
            <Stack
                direction="column"
                component="main"
                sx={[
                    {
                        justifyContent: 'center',
                        height: 'calc((1 - var(--template-frame-height, 0)) * 100%)',
                        marginTop: 'max(40px - var(--template-frame-height, 0px), 0px)',
                        minHeight: '100%',
                    },
                    (theme) => ({
                        '&::before': {
                            content: '""',
                            display: 'block',
                            position: 'absolute',
                            zIndex: -1,
                            inset: 0,
                            backgroundImage:
                                'radial-gradient(ellipse at 50% 50%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))',
                            backgroundRepeat: 'no-repeat',
                            ...theme.applyStyles('dark', {
                                backgroundImage:
                                    'radial-gradient(at 50% 50%, hsla(210, 100%, 16%, 0.5), hsl(220, 30%, 5%))',
                            }),
                        },
                    }),
                ]}
            >
                <Stack
                    direction={{ xs: 'column-reverse', md: 'row' }}
                    sx={{
                        justifyContent: 'center',
                        gap: { xs: 6, sm: 12 },
                        p: 2,
                        mx: 'auto',
                    }}
                >
                    <Stack
                        direction={{ xs: 'column-reverse', md: 'row' }}
                        sx={{
                            justifyContent: 'center',
                            gap: { xs: 6, sm: 12 },
                            p: { xs: 2, sm: 4 },
                            m: 'auto',
                        }}
                    >
                        <Stack
                            sx={{ flexDirection: 'column', alignSelf: 'center', gap: 4, maxWidth: 450 }}
                        >
                            {/* <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
                                <SitemarkIcon />
                            </Box> */}
                            {items.map((item, index) => (
                                <Stack key={index} direction="row" sx={{ gap: 2 }}>
                                    {item.icon}
                                    <div>
                                        <Typography gutterBottom sx={{ fontWeight: 'medium' }}>
                                            {item.title}
                                        </Typography>
                                        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                            {item.description}
                                        </Typography>
                                    </div>
                                </Stack>
                            ))}
                        </Stack>
                        <Card variant="outlined">
                            {/* <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
                                <SitemarkIcon />
                            </Box> */}
                            <Typography
                                component="h1"
                                variant="h4"
                                sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
                            >
                                Sign in
                            </Typography>
                            <Box
                                component="form"
                                onSubmit={handleSubmit}
                                noValidate
                                sx={{ display: 'flex', flexDirection: 'column', width: '100%', gap: 2 }}
                            >
                                <FormControl>
                                    <FormLabel htmlFor="email">Email</FormLabel>
                                    <TextField
                                        // error={emailError}
                                        // helperText={emailErrorMessage}
                                        id="email"
                                        type="email"
                                        name="email"
                                        placeholder="your@email.com"
                                        autoComplete="email"
                                        autoFocus
                                        required
                                        fullWidth
                                        variant="outlined"
                                        color={emailError ? 'error' : 'primary'}
                                    />
                                </FormControl>
                                <FormControl>
                                    <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                                        <FormLabel htmlFor="password">Password</FormLabel>
                                        <Link
                                            component="button"
                                            type="button"
                                            // onClick={handleClickOpen}
                                            variant="body2"
                                            sx={{ alignSelf: 'baseline' }}
                                        >
                                            Forgot your password?
                                        </Link>
                                    </Box>
                                    <TextField
                                        // error={passwordError}
                                        // helperText={passwordErrorMessage}
                                        name="password"
                                        placeholder="••••••"
                                        type="password"
                                        id="password"
                                        autoComplete="current-password"
                                        autoFocus
                                        required
                                        fullWidth
                                        variant="outlined"
                                        color={passwordError ? 'error' : 'primary'}
                                    />
                                </FormControl>
                                <FormControlLabel
                                    control={<Checkbox value="remember" color="primary" />}
                                    label="Remember me"
                                />
                                {/* <ForgotPassword open={open} handleClose={handleClose} /> */}
                                <Button type="submit" fullWidth variant="contained" onClick={() => {}}>
                                    Sign in
                                </Button>
                                <Typography sx={{ textAlign: 'center' }}>
                                    Don&apos;t have an account?{' '}
                                    <span>
                                        <Link
                                            href="/material-ui/getting-started/templates/sign-in/"
                                            variant="body2"
                                            sx={{ alignSelf: 'center' }}
                                        >
                                            Sign up
                                        </Link>
                                    </span>
                                </Typography>
                            </Box>
                            <Divider>or</Divider>
                            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                                <Button
                                    fullWidth
                                    variant="outlined"
                                    onClick={() => alert('Sign in with Google')}
                                    startIcon={<GoogleIcon />}
                                >
                                    Sign in with Google
                                </Button>
                                <Button
                                    fullWidth
                                    variant="outlined"
                                    onClick={() => alert('Sign in with Facebook')}
                                    startIcon={<FacebookIcon />}
                                >
                                    Sign in with Facebook
                                </Button>
                            </Box>
                        </Card>
                    </Stack>
                </Stack>
            </Stack>
        </>
    );
}
