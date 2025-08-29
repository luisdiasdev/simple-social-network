import { Avatar, Grid, Typography } from "@mui/material";

export default function Brand() {
    return (
        <Grid container direction="column" alignItems="center">
            <Grid>
                <Avatar sx={{ fontSize: "2.2em", m: 1, color: "secondary.main", backgroundColor: "primary.main" }}>C</Avatar>
            </Grid>
            <Grid>
                <Typography component="h3" variant="h4">Social Network</Typography>
            </Grid>
        </Grid>
    );
}