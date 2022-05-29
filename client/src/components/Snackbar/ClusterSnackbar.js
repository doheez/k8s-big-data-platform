import { Snackbar, CircularProgress, IconButton } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { Fragment } from "react";

const CREATING_CLUSTER = "Creating Cluster...";
const DELETING_CLUSTER = 'Deleting Cluster...';
const INCREASING_NODES = "Increasing Nodes...";
const DECREASING_NODES = "Decreasing Nodes...";

const ClusterSnackbar = ({ message, handleCloseSnackbar, openSnackbar }) => {
  const action = (
    <Fragment>
      {message === CREATING_CLUSTER || message === DELETING_CLUSTER ||
        message === INCREASING_NODES || message === DECREASING_NODES ?
        <CircularProgress size={30} sx={{ color: "primary.light" }} />
        :
        <IconButton
          size="small"
          aria-label="close"
          color="inherit"
          onClick={handleCloseSnackbar}
        >
          <CloseIcon fontSize="small" />
        </IconButton>
      }
    </Fragment>
  );

  return (
    <Snackbar
      open={openSnackbar}
      autoHideDuration={6000}
      onClose={handleCloseSnackbar}
      message={message}
      action={action}
      anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
    />
  );
}

export default ClusterSnackbar;