import { Stack, Typography, TextField, Button, Link, Box } from '@mui/material';

export default function LoginEmail() {
  return (
    <Box>
      <Typography variant="h4" align="center">
        Log In
      </Typography>
      <Stack spacing={2} mt={5}>
        <TextField
          id="email"
          label="Email"
          variant="outlined"
          autoComplete="off"
          type="email"
          size="small" />
        <TextField
          id="password"
          label="Password"
          variant="outlined"
          autoComplete="off"
          type="password"
          size="small" />
        <Button variant="contained">
          Log In
        </Button>
      </Stack>
      <Typography align="center" mt={5}>
        <Link href="#" underline="hover">Forgot Password?</Link><br />
        No Account? <Link href="#" underline="hover">Create One.</Link>
      </Typography>
    </Box>
  );
}