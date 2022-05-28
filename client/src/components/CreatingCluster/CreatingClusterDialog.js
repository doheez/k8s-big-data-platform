import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { List, ListItem } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';

export default function CreatingClusterDialog({ open, setOpen, cluster }) {
  const [clusterNumber, setClusterNumber] = useState(0);
  const [clusterName, setClusterName] = useState('');

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleCreateCluster = () => {
    handleClose();

    const url = '/api/cluster/create';
    const data = {
      type: cluster,
      name: clusterName,
      amount: clusterNumber
    };

    axios.post(url, data)
      .then(response => {
        alert(`Success in creating ${cluster} cluster!`);
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

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Create {capitalize(cluster)} Cluster</DialogTitle>
        <DialogContent>
          <List>
            <ListItem sx={{ px: 0 }}>
              <TextField
                autoFocus
                autoComplete="off"
                size="small"
                helperText="Enter the number of clusters you want to create."
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
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleCreateCluster}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
