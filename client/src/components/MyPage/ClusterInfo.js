import { Box, Button, Divider, List, ListItem, Stack, Typography, Grid, Pagination } from "@mui/material";
import { useState } from "react";
import IncreaseDecreaseNodeDialog from "./ClusterManagementDialog/IncreaseDecreaseNodeDialog";
import DeleteClusterDialog from "./ClusterManagementDialog/DeleteClusterDialog";
import PodTable from "./PodTable";

export default function ClusterInfo({ cluster, clusterList }) {
  const [openIncreaseDecrease, setOpenIncreaseDecrease] = useState(false);
  const [openDelete, setOpenDelete] = useState(false);
  const [option, setOption] = useState('increase');
  const [page, setPage] = useState(1);

  const handleClickIncreaseDecrease = (opt) => {
    setOption(opt);
    setOpenIncreaseDecrease(true);
  };

  const handleClickDelete = () => {
    setOpenDelete(true);
  }

  const handleChange = (event, value) => {
    setPage(value);
  };

  return (
    <Box sx={{ backgroundColor: "mypageBox.main", borderRadius: 1 }}>
      {clusterList.map((clusterInfo, index) => {
        return (
          <div hidden={index !== (page - 1)} key={clusterInfo.clusterName}>
            {index === (page - 1) &&
              <List>
                <ListItem>
                  <Stack>
                    <Typography variant="subtitle2" color="text.secondary">Cluster ID</Typography>
                    <Typography color="text.primary">{clusterInfo.clusterName}</Typography>
                  </Stack>
                </ListItem>
                <Divider />
                <ListItem>
                  <Stack>
                    <Typography variant="subtitle2" color="text.secondary">Numbers of Clusters</Typography>
                    <Typography color="text.primary">{clusterInfo.podInfos.length}</Typography>
                  </Stack>
                </ListItem>
                <Divider />
                <ListItem>
                  <Stack width="100%">
                    <Typography variant="subtitle2" color="text.secondary">Pods Status</Typography>
                    <PodTable cluster={cluster} pods={clusterInfo.podInfos} />
                  </Stack>
                </ListItem>
                <Divider />
                <ListItem>
                  <Stack>
                    <Typography variant="subtitle2" color="text.secondary">
                      {cluster === 'hadoop' ? 'Size of Data' : 'Throughput'}
                    </Typography>
                    <Typography color="text.primary">그래프 삽입 예정</Typography>
                  </Stack>
                </ListItem>
                <Divider />
                <ListItem>
                  <Stack width="100%" rowGap={1}>
                    <Typography variant="subtitle2" color="text.secondary">Cluster Management</Typography>
                    <Grid container spacing={2}>
                      <Grid item xs={4}>
                        <Button variant="contained" fullWidth onClick={() => handleClickIncreaseDecrease('increase')}>increase node</Button>
                        <IncreaseDecreaseNodeDialog open={openIncreaseDecrease} setOpen={setOpenIncreaseDecrease} cluster={cluster} option={option} clusterName={clusterInfo.clusterName} />
                      </Grid>
                      <Grid item xs={4}>
                        <Button variant="contained" fullWidth onClick={() => handleClickIncreaseDecrease('decrease')}>decrease node</Button>
                      </Grid>
                      <Grid item xs={4}>
                        <Button variant="contained" fullWidth onClick={() => handleClickDelete()}>delete cluster</Button>
                        <DeleteClusterDialog open={openDelete} setOpen={setOpenDelete} cluster={cluster} clusterName={clusterInfo.clusterName} />
                      </Grid>
                    </Grid>
                  </Stack>
                </ListItem>
              </List>
            }
          </div>
        );
      })}
      <Pagination count={clusterList.length} page={page} onChange={handleChange} sx={{ py: 2, display: 'flex', justifyContent: 'center' }} />
    </Box>
  );
}