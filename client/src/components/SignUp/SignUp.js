import { useState } from "react";
import { Container, Box, Typography, Stack, TextField, Button } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

export default function SignUp() {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
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

  const handleSubmit = () => {
    const url = '/api/signup';
    const data = {
      name: name,
      email: email,
      password: password
    };

    if (checkEmail(email)) {
      axios.post(url, data)
        .then(response => {
          console.log(response);
          alert('Success in sign up! Please log in.');
          navigate('/login');
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  return (
    <Container maxWidth="sm">
      <Box
        my={7}
        p={8}
        sx={{ border: 1, borderRadius: 1, borderColor: 'primary.main' }}>
        <Box>
          <Typography variant="h4" align="center">
            Sign Up
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
              id="name"
              label="Name"
              variant="outlined"
              autoComplete="off"
              type="text"
              size="small"
              onChange={e => { setName(e.target.value) }}
              value={name}
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
            <Button variant="contained" size="large" onClick={() => handleSubmit()}>
              Sign Up
            </Button>
          </Stack>
        </Box>
      </Box>
    </Container>
  );
}