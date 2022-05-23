import * as React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Alert, AlertTitle } from '@mui/material';

export default function DeleteClusterDialog({ open, setOpen, cluster }) {
  const handleClose = () => {
    setOpen(false);
  };

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Delete {capitalize(cluster)} Cluster</DialogTitle>
        <DialogContent>
          <Alert severity="warning">
            <AlertTitle>Warning</AlertTitle>
            If you delete the cluster, <strong>you cannot recover it again.</strong> Do you still want to continue?</Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleClose}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}