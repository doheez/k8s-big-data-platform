import { Stack, Avatar, AvatarGroup, Tooltip, Button } from "@mui/material";
import { stringAvatar } from "../Utils/Utils";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { useState } from 'react';
import InvitedUserListDialog from "./InvitedUserListDialog";

const InvitedUserList = ({ userList }) => {
  const [open, setOpen] = useState(false);

  return (
    <Stack direction="row">
      <AvatarGroup max={4}>
        {userList.map(user =>
          <Tooltip title={`${user.name} (${user.email})`} key={user.email}>
            <Avatar {...stringAvatar(user.name)} />
          </Tooltip>
        )}
      </AvatarGroup>
      <Button onClick={() => setOpen(true)} startIcon={<AccountCircleIcon />} variant="outlined" sx={{ ml: 2 }}>Show All</Button>
      <InvitedUserListDialog open={open} setOpen={setOpen} userList={userList} />
    </Stack>
  );
}

export default InvitedUserList;