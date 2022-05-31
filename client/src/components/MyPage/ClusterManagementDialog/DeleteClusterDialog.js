import * as React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Alert, AlertTitle } from '@mui/material';
import axios from 'axios';
import { useState } from 'react';
import ClusterSnackbar from '../../Snackbar/ClusterSnackbar';

const DELETING_CLUSTER = 'Deleting Cluster...';
const SUCCESS_IN_DELETING_CLUSTER = "✅ Cluster Deleted Successfully!";
const FAIL_IN_DELETING_CLUSTER = "⛔ Failed to Delete Cluster.";

export default function DeleteClusterDialog({ open, setOpen, cluster, clusterName }) {
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [message, setMessage] = useState(DELETING_CLUSTER);

  const handleCloseDialog = () => {
    setOpen(false);
  };

  const handleOpenSnackbar = () => {
    setMessage(DELETING_CLUSTER);
    setOpenSnackbar(true);
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  }

  const handleSuccessDeletingCluster = () => {
    setMessage(SUCCESS_IN_DELETING_CLUSTER);
  };

  const handleFailDeletingCluster = () => {
    setMessage(FAIL_IN_DELETING_CLUSTER);
  };

  const handleDeleteCluster = () => {
    handleCloseDialog();
    handleOpenSnackbar();

    const url = `/api/cluster/${clusterName}`;

    axios.delete(url)
      .then(response => {
        handleSuccessDeletingCluster();
        console.log(response);
      })
      .catch(error => {
        handleFailDeletingCluster();
        console.log(error);
      });
  };

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleCloseDialog}>
        <DialogTitle>Delete {capitalize(cluster)} Cluster</DialogTitle>
        <DialogContent>
          <Alert severity="warning">
            <AlertTitle>Warning</AlertTitle>
            If you delete the cluster, <strong>you cannot recover it again.</strong> Do you still want to continue?</Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button onClick={() => handleDeleteCluster()}>OK</Button>
        </DialogActions>
      </Dialog>
      <ClusterSnackbar message={message} handleCloseSnackbar={handleCloseSnackbar} openSnackbar={openSnackbar} />
    </div>
  );
}