import { Container, Box } from "@mui/material";
import LoginEmail from "./LoginEmail";

export default function Login() {
  return (
    <Container maxWidth="sm">
      <Box
        mt={10}
        p={5}
        sx={{ border: 1, borderRadius: 1, borderColor: 'primary.main' }}>
        <LoginEmail />
      </Box>
    </Container>
  );
}