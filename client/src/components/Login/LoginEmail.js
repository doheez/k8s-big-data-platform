import { Stack, Typography, TextField, Button, Link, Box } from '@mui/material';
import axios from "axios";
import { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { useNavigate } from "react-router-dom";

export default function LoginEmail({ setToken }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isEmail, setIsEmail] = useState(true);
  const navigate = useNavigate();

  // 이메일 유효성 검사
  const checkEmail = (email) => {
    const emailRegex =
      /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    const result = emailRegex.test(email);
    setIsEmail(result);
    return result;
  };

  const handleEmailLogin = () => {
    const url = '/api/login';
    const data = {
      email: email,
      password: password
    };

    if (checkEmail(email)) {
      axios.post(url, data)
        .then(response => {
          setToken(response.data.token);
          console.log(response);
          alert('Success in login!');
          navigate('/');
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  return (
    <Box>
      <Typography variant="h4" align="center">
        Log In
      </Typography>
      <Stack spacing={1.5} mt={3}>
        <TextField
          id="email"
          label="Email"
          variant="outlined"
          autoComplete="off"
          type="email"
          size="small"
          onChange={e => { setEmail(e.target.value) }}
          value={email}
          error={(!isEmail && true)}
          helperText={(!isEmail && "Not a valid email format.")}
        />
        <TextField
          id="password"
          label="Password"
          variant="outlined"
          autoComplete="off"
          type="password"
          size="small"
          onChange={e => { setPassword(e.target.value) }}
          value={password}
        />
        <Button variant="contained" size="large" onClick={() => handleEmailLogin()}>
          Log In
        </Button>
      </Stack>
      <Typography align="center" mt={3}>
        <Link href="#" underline="hover">Forgot Password?</Link><br />
        No Account? <Link component={RouterLink} to="/signup" underline="hover">Create One.</Link>
      </Typography>
    </Box>
  );
}