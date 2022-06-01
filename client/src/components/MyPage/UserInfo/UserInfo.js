import { Avatar, Box, Stack, Typography } from "@mui/material";
import axios from "axios";
import { useState, useEffect } from "react";
import { stringAvatar } from "../Utils/Utils";

export default function UserInfo() {
  const [name, setName] = useState();
  const [email, setEmail] = useState();

  const getUserInfo = () => {
    const url = '/api/user';

    axios.get(url)
      .then(response => {
        setName(response.data.name);
        setEmail(response.data.email);
      })
      .catch(error => {
        if (error.response.data) {
          alert(error.response.data);
        } else {
          alert(error.message);
        }
        console.log(error);
      });
  };

  useEffect(() => {
    getUserInfo();
  }, [name]);

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