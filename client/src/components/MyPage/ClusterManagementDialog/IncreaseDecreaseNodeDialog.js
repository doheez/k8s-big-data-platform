import * as React from 'react';
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

export default function IncreaseDecreaseNodeDialog({ open, setOpen, cluster, option }) {
  const handleClose = () => {
    setOpen(false);
  };

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  const HELPER_TEXT = `Enter the number of nodes you want to ${option}.`;

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{capitalize(option)} {capitalize(cluster)} Node</DialogTitle>
        <DialogContent>
          <TextField
            sx={{ my: 1 }}
            autoFocus
            autoComplete="off"
            size="small"
            helperText={HELPER_TEXT}
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