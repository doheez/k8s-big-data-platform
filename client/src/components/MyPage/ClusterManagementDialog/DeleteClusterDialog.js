import * as React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Alert, AlertTitle } from '@mui/material';
import axios from 'axios';

export default function DeleteClusterDialog({ open, setOpen, cluster, clusterName }) {
  const handleClose = () => {
    setOpen(false);
  };

  const handleDeleteCluster = () => {
    handleClose();

    const url = '/api/cluster/modify';
    const data = {
      type: cluster,
      name: clusterName,
      amount: 0
    };

    axios.post(url, data)
      .then(response => {
        alert(`Success in delete ${clusterName} cluster!`);
        console.log(response);
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
          <Button onClick={() => handleDeleteCluster()}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}