import { Avatar, Box, Stack, Typography } from "@mui/material";

export default function UserInfo() {
  return (
    <Box p={2} sx={{ backgroundColor: "mypageBox.main" }}>
      <Stack direction="row" spacing={2}>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <Avatar>K</Avatar>
        </Box>
        <Stack>
          <Typography variant="h6" color="text.primary">Apache Kim</Typography>
          <Typography color="text.secondary">kubernetes@gmail.com</Typography>
        </Stack>
      </Stack>
    </Box>
  );
}