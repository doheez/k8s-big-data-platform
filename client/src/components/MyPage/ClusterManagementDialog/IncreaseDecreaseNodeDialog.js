import * as React from 'react';
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { useState, useEffect } from 'react';
import axios from 'axios';
import ClusterSnackbar from '../../Snackbar/ClusterSnackbar';

const INCREASE = "increase", DECREASE = "decrease";

const INCREASING_NODES = "Increasing Nodes...";
const DECREASING_NODES = "Decreasing Nodes...";
const SUCCESS_IN_INCREASING_NODES = "✅ Nodes Increased Successfully!";
const SUCCESS_IN_DECREASING_NODES = "✅ Nodes Decreased Successfully!";
const FAIL_IN_INCREASING_NODES = "⛔ Failed to Increase Nodes.";
const FAIL_IN_DECREASING_NODES = "⛔ Failed to Decrease Nodes.";

export default function IncreaseDecreaseNodeDialog({ open, setOpen, cluster, option, clusterName, clusterAmount }) {
  const [amount, setAmount] = useState();
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [message, setMessage] = useState(INCREASING_NODES);

  const handleCloseDialog = () => {
    setOpen(false);
    setAmount(0);
  };

  const handleOpenSnackbar = () => {
    if (option === INCREASE) {
      setMessage(INCREASING_NODES);
    } else {
      setMessage(DECREASING_NODES);
    }
    setOpenSnackbar(true);
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  }

  const handleSuccessModifyingCluster = () => {
    if (clusterAmount === amount) {
      if (option === INCREASE) {
        setMessage(SUCCESS_IN_INCREASING_NODES);
      } else {
        setMessage(SUCCESS_IN_DECREASING_NODES);
      }
    }
  };

  const handleFailModifyingCluster = () => {
    if (option === INCREASE) {
      setMessage(FAIL_IN_INCREASING_NODES);
    } else {
      setMessage(FAIL_IN_DECREASING_NODES);
    }
  };

  useEffect(() => {
    handleSuccessModifyingCluster();
  });

  const handleScaleCluster = () => {
    handleOpenSnackbar();

    const url = '/api/cluster/modify';
    const data = {
      type: cluster,
      name: clusterName,
      amount: amount
    };

    axios.post(url, data)
      .then(response => {
        handleSuccessModifyingCluster();
        console.log(response);
      })
      .catch(error => {
        handleFailModifyingCluster();
        console.log(error);
      });

    handleCloseDialog();
  };

  const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };

  const HELPER_TEXT = `Enter the number of nodes you want to ${option}.`;

  return (
    <div>
      <Dialog open={open} onClose={handleCloseDialog}>
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
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button onClick={() => handleScaleCluster()} disabled={!amount}>OK</Button>
        </DialogActions>
      </Dialog>
      <ClusterSnackbar message={message} handleCloseSnackbar={handleCloseSnackbar} openSnackbar={openSnackbar} />
    </div>
  );
}