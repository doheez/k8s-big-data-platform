import * as React from 'react';
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';

export default function IncreaseDecreaseNodeDialog({ open, setOpen, cluster, option, clusterName }) {
  const [amount, setAmount] = useState();

  const handleClose = () => {
    setOpen(false);
  };

  const handleScaleCluster = () => {
    handleClose();

    const url = '/api/cluster/modify';
    const data = {
      type: cluster,
      name: clusterName,
      amount: amount
    };

    axios.post(url, data)
      .then(response => {
        alert(`Success in ${option} ${clusterName} nodes!`);
        console.log(response);
      })
      .catch(error => {
        alert(error.message);
        console.log(error);
      });
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
            onChange={e => setAmount(Number(e.target.value))}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={() => handleScaleCluster()}>OK</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}