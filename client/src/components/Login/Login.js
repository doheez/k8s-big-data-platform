import { Container, Box } from "@mui/material";
import LoginEmail from "./LoginEmail";
import LoginSNS from "./LoginSNS";

export default function Login() {
  return (
    <Container maxWidth="sm">
      <Box
        my={7}
        p={8}
        sx={{ border: 1, borderRadius: 1, borderColor: 'primary.main' }}>
        <LoginEmail />
        <LoginSNS />
      </Box>
    </Container>
  );
}