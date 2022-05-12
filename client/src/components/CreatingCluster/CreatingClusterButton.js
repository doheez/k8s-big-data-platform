import { Button, ButtonGroup, Grid, Divider } from "@mui/material";
import { ReactComponent as HadoopLogo } from '../../images/hadoopLogo.svg';
import { ReactComponent as SparkLogo } from '../../images/sparkLogo.svg';
import { useState } from 'react';
import CreatingClusterDialog from "./CreatingClusterDialog";
import { StyledEngineProvider } from '@mui/material/styles';
import "./CreatingCluster.css";

export default function CreatingClusterButton() {
  const [open, setOpen] = useState(false);
  const [cluster, setCluster] = useState('');

  const clusterName = ['Hadoop', 'Spark'];

  const handleClickOpen = (clusterName) => {
    setCluster(clusterName);
    setOpen(true);
  };

  return (
    <StyledEngineProvider injectFirst>
      <ButtonGroup variant="outlined" aria-label="creating clusters button group" fullWidth size="large">
        <Button
          sx={{ p: 0, pt: '7px' }}
          onClick={() => handleClickOpen(clusterName[0])}>
          <Grid container direction="column">
            <Grid item>
              <HadoopLogo height="100px" />
            </Grid>
            <Divider sx={{ borderColor: 'rgba(123, 31, 162, 0.5)' }} />
            <Grid item py="7px">
              Create Hadoop Cluster
            </Grid>
          </Grid>
        </Button>
        <Button
          sx={{ p: 0, pt: '7px' }}
          onClick={() => handleClickOpen(clusterName[1])}>
          <Grid container direction="column">
            <Grid item>
              <SparkLogo height="100px" />
            </Grid>
            <Divider sx={{ borderColor: 'rgba(123, 31, 162, 0.5)' }} />
            <Grid item py="7px">
              Create Spark Cluster
            </Grid>
          </Grid>
        </Button>
      </ButtonGroup>
      <CreatingClusterDialog open={open} setOpen={setOpen} cluster={cluster} />
    </StyledEngineProvider>
  );
}