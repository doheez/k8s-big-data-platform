import * as React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

export default function PodDetailDialog({ open, setOpen, cluster }) {
  const handleClose = () => {
    setOpen(false);
  };

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{capitalize(cluster)} Cluster Pod Details</DialogTitle>
        <DialogContent>
          해당 파드에 대한 정보 추가 예정
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleClose}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}