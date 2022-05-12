import UserInfo from './UserInfo';
import ClusterInfo from './ClusterInfo';
import { Container, Grid, Typography } from '@mui/material';

export default function MyPage() {
  return (
    <Container maxWidth="lg">
      <Grid container direction={{ xs: "column", md: "row" }} spacing={3} my={3}>
        <Grid item xs md={4}>
          <Typography variant="h6" color="primary.main">User Info</Typography>
          <UserInfo />
        </Grid>
        <Grid item xs md container direction="column" spacing={{ xs: 0, md: 3 }}>
          <Grid item xs>
            <Typography variant="h6" color="primary.main">Hadoop Cluster Info</Typography>
            <ClusterInfo cluster="hadoop" />
          </Grid>
          <Grid item xs>
            <Typography variant="h6" color="primary.main">Spark Cluster Info</Typography>
            <ClusterInfo cluster="spark" />
          </Grid>
        </Grid>
      </Grid>
    </Container>
  );
}