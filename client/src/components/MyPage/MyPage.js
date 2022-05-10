import UserInfo from './UserInfo';
import HadoopClusterInfo from './HadoopClusterInfo';
import SparkClusterInfo from './SparkClusterInfo';
import { Container, Grid, Typography } from '@mui/material';

export default function MyPage() {
  return (
    <Container maxWidth="lg">
      <Grid container columnSpacing={3} my={7}>
        <Grid item xs={4}>
          <Typography variant="h6" color="primary.main">User Info</Typography>
          <UserInfo />
        </Grid>
        <Grid item xs container spacing={3} direction="column">
          <Grid item xs>
            <Typography variant="h6" color="primary.main">Hadoop Cluster Info</Typography>
            <HadoopClusterInfo />
          </Grid>
          <Grid item xs>
            <Typography variant="h6" color="primary.main">Spark Cluster Info</Typography>
            <SparkClusterInfo />
          </Grid>
        </Grid>
      </Grid>
    </Container>
  );
}