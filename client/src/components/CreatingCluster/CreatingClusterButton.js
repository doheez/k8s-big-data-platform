import { Button, ButtonGroup, Grid, Divider } from "@mui/material";
import { ReactComponent as HadoopLogo } from '../../images/hadoopLogo.svg';
import { ReactComponent as SparkLogo } from '../../images/sparkLogo.svg';

export default function CreatingClusterButton() {
  return (
    <ButtonGroup variant="outlined" aria-label="creating clusters button group" fullWidth size="large">
      <Button sx={{ px: 0, py: 0, pt: '7px' }}>
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
      <Button sx={{ px: 0, py: 0, pt: '7px' }}>
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
  );
}