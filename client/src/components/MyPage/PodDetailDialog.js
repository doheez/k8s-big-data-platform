import * as React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, List, ListItem, ListItemAvatar, Avatar, Divider, Typography, Stack } from '@mui/material';
import LabelIcon from '@mui/icons-material/LabelOutlined';
import LocalOfferIcon from '@mui/icons-material/LocalOfferOutlined';
import HolidayVillageIcon from '@mui/icons-material/HolidayVillageOutlined';
import BusinessIcon from '@mui/icons-material/Business';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import SignalWifi0BarIcon from '@mui/icons-material/SignalWifi0Bar'; import FlipIcon from '@mui/icons-material/Flip';

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
          <List>
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <LocalOfferIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack>
                <Typography variant="subtitle2" color="text.secondary">Name</Typography>
                <Typography color="text.primary">example-hadoopservice-hadoop-slave-0</Typography>
              </Stack>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <HolidayVillageIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack>
                <Typography variant="subtitle2" color="text.secondary">Namespace</Typography>
                <Typography color="text.primary">hadoop</Typography>
              </Stack>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <LabelIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack width="100%">
                <Typography variant="subtitle2" color="text.secondary">Node Name</Typography>
                <Typography color="text.primary">gke-cluster-1-default-pool-bd9844b9-f2lf</Typography>
              </Stack>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <BusinessIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack width="100%">
                <Typography variant="subtitle2" color="text.secondary">Node IP</Typography>
                <Typography color="text.primary">10.128.0.9</Typography>
              </Stack>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <AccessTimeIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack width="100%">
                <Typography variant="subtitle2" color="text.secondary">Start Time</Typography>
                <Typography color="text.primary">Thu, 26 May 2022 12:58:29</Typography>
              </Stack>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <SignalWifi0BarIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack width="100%">
                <Typography variant="subtitle2" color="text.secondary">Status</Typography>
                <Typography color="text.primary">RUNNING</Typography>
              </Stack>
            </ListItem>
            <Divider />
            <ListItem>
              <ListItemAvatar>
                <Avatar variant="square">
                  <FlipIcon />
                </Avatar>
              </ListItemAvatar>
              <Stack width="100%">
                <Typography variant="subtitle2" color="text.secondary">IP</Typography>
                <Typography color="text.primary">10.4.0.104</Typography>
              </Stack>
            </ListItem>
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Close</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}