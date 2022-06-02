import * as React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, List, ListItem, ListItemAvatar, ListItemText, Avatar, Divider } from '@mui/material';
import { stringAvatar } from '../Utils/Utils';

export default function InvitedUserListDialog({ open, setOpen, userList }) {
  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>All Administrators List</DialogTitle>
        <DialogContent>
          <List>
            {userList.map((user, index) =>
              <div key={user.email}>
                {index !== 0 && <Divider />}
                <ListItem>
                  <ListItemAvatar>
                    <Avatar {...stringAvatar(user.name)} />
                  </ListItemAvatar>
                  <ListItemText primary={user.name} secondary={user.email} />
                </ListItem>
              </div>
            )}
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Close</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}