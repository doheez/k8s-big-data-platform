import { Box, Container, Grid, Typography } from '@mui/material';
import folder from '../../images/folder.png';

export default function Banner() {
  return (
    <Box sx={{ backgroundColor: 'primary.main', color: 'primary.contrastText' }}>
      <Container maxWidth="lg">
        <Grid container direction="row">
          <Grid item xs={2.5} lg={2} sx={{ display: { xs: 'none', md: 'flex' }, alignItems: 'center' }}>
            <img src={folder} width="150px" alt="banner" />
          </Grid>
          <Grid item xs>
            <Grid container direction="column">
              <Grid item mt={8}>
                <Typography variant="h3">Configure Hadoop/Spark Clusters</Typography>
              </Grid>
              <Grid item>
                <Typography variant="h3">in a Cloud-Native Environment</Typography>
              </Grid>
              <Grid item mt={2} mb={8}>
                <Typography variant="h5">Experience the highest speed in big data processing!</Typography>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}