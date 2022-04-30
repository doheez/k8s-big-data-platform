import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { List, ListItem } from '@mui/material';

export default function CreatingClusterDialog({ open, setOpen, cluster }) {
  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Create {cluster} Cluster</DialogTitle>
        <DialogContent>
          <List>
            <ListItem sx={{px: 0}}>
              <TextField
                autoFocus
                autoComplete="off"
                size="small"
                helperText="Enter the number of clusters you want to create."
                id="clusterNumber"
                type="number"
                label="Number"
              />
            </ListItem>
            <ListItem sx={{px: 0}}>
              <TextField
                autoComplete="off"
                size="small"
                fullWidth
                helperText="Enter the name of cluster."
                id="clusterName"
                label="Name"
              />
            </ListItem>
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleClose}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
