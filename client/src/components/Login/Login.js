import { Container, Box } from "@mui/material";
import LoginEmail from "./LoginEmail";
import LoginSNS from "./LoginSNS";
import axios from 'axios';

export default function Login({ setToken }) {
  return (
    <Container maxWidth="sm">
      <Box
        my={7}
        p={8}
        sx={{ border: 1, borderRadius: 1, borderColor: 'primary.main' }}>
        <LoginEmail setToken={setToken} />
        <LoginSNS />
      </Box>
    </Container>
  );
}