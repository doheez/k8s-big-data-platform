import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle, List, ListItem } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import ClusterSnackbar from '../Snackbar/ClusterSnackbar';

const CREATING_CLUSTER = "Creating Cluster...";
const SUCCESS_IN_CREATING_CLUSTER = "✅ Cluster Created Successfully!";
const FAIL_IN_CREATING_CLUSTER = "⛔ Failed to Create Cluster.";

export default function CreatingClusterDialog({ open, setOpen, cluster }) {
  const [clusterNumber, setClusterNumber] = useState(0);
  const [clusterName, setClusterName] = useState('');
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [message, setMessage] = useState(CREATING_CLUSTER);

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  const handleCloseDialog = () => {
    setOpen(false);
  };

  const handleOpenSnackbar = () => {
    setMessage(CREATING_CLUSTER);
    setOpenSnackbar(true);
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  }

  const handleSuccessCreatingCluster = () => {
    setMessage(SUCCESS_IN_CREATING_CLUSTER);
  };

  const handleFailCreatingCluster = () => {
    setMessage(FAIL_IN_CREATING_CLUSTER);
  };

  const handleCreateCluster = () => {
    handleCloseDialog();
    handleOpenSnackbar();

    const url = '/api/cluster/create';
    const data = {
      type: cluster,
      name: clusterName,
      amount: clusterNumber
    };

    axios.post(url, data)
      .then(response => {
        handleSuccessCreatingCluster();
        console.log(response);
      })
      .catch(error => {
        handleFailCreatingCluster();
        console.log(error);
      });
  };

  return (
    <div>
      <Dialog open={open} onClose={handleCloseDialog}>
        <DialogTitle>Create {capitalize(cluster)} Cluster</DialogTitle>
        <DialogContent>
          <List>
            <ListItem sx={{ px: 0 }}>
              <TextField
                autoFocus
                autoComplete="off"
                size="small"
                helperText="Enter the number of nodes you want to create."
                id="clusterNumber"
                type="number"
                label="Number"
                onChange={e => setClusterNumber(Number(e.target.value))}
              />
            </ListItem>
            <ListItem sx={{ px: 0 }}>
              <TextField
                autoComplete="off"
                size="small"
                fullWidth
                helperText="Enter the name of cluster."
                id="clusterName"
                label="Name"
                onChange={e => setClusterName(e.target.value)}
              />
            </ListItem>
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button onClick={handleCreateCluster}>OK</Button>
        </DialogActions>
      </Dialog>
      <ClusterSnackbar message={message} handleCloseSnackbar={handleCloseSnackbar} openSnackbar={openSnackbar} />
    </div>
  );
}