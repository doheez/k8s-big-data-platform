import * as React from 'react';
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

export default function AddClusterDialog({ open, setOpen, cluster }) {
  const handleClose = () => {
    setOpen(false);
  };

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Add {capitalize(cluster)} Cluster</DialogTitle>
        <DialogContent>
          <TextField
            sx={{ my: 1 }}
            autoFocus
            autoComplete="off"
            size="small"
            helperText="Enter the number of clusters you want to add."
            id="clusterNumber"
            type="number"
            label="Number"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleClose}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}