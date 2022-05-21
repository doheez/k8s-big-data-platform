import { Avatar, Box, Stack, Typography } from "@mui/material";
import axios from "axios";
import { useState } from "react";

export default function UserInfo() {
  const [name, setName] = useState();
  const [email, setEmail] = useState();

  const getUserInfo = () => {
    const url = '/api/user';

    axios.get(url)
      .then(response => {
        console.log(response)
        setName(response.data.name);
        setEmail(response.data.email);
      })
      .catch(error => {
        console.log(error);
      })
  };
  if (name === undefined) {
    getUserInfo();
  }

  function stringAvatar(name) {
    if (name === undefined) {
      return;
    }
    return {
      children: name[0].toUpperCase()
    };
  }

  return (
    <Box p={2} sx={{ backgroundColor: "mypageBox.main", borderRadius: 1 }}>
      <Stack direction="row" spacing={2}>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <Avatar {...stringAvatar(name)} />
        </Box>
        <Stack>
          <Typography variant="h6" color="text.primary">{name}</Typography>
          <Typography color="text.secondary">{email}</Typography>
        </Stack>
      </Stack>
    </Box>
  );
}