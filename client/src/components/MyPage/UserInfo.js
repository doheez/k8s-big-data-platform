import { Avatar, Box, Stack, Typography } from "@mui/material";
import axios from "axios";
import { useState, useEffect } from "react";

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

  function stringToColor(string) {
    let hash = 0;
    let i;

    /* eslint-disable no-bitwise */
    for (i = 0; i < string.length; i += 1) {
      hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = '#';

    for (i = 0; i < 3; i += 1) {
      const value = (hash >> (i * 8)) & 0xff;
      color += `00${value.toString(16)}`.slice(-2);
    }
    /* eslint-enable no-bitwise */

    return color;
  }

  function stringAvatar(name) {
    if (name === undefined) {
      return;
    }
    return {
      sx: {
        bgcolor: stringToColor(name),
      },
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