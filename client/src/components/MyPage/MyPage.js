import UserInfo from './UserInfo';
import ClusterInfo from './ClusterInfo';
import { Container, Grid, Typography, Stack, CircularProgress, Tabs, Tab, Box } from '@mui/material';
import { useState, useEffect } from "react";
import axios from 'axios';
import { a11yProps, TabPanel } from './TabPanel';

const HADOOP = 'hadoop', SPARK = 'spark';

export default function MyPage() {
  const [hadoopClusterList, setHadoopClusterList] = useState([]);
  const [sparkClusterList, setSparkClusterList] = useState([]);
  const [value, setValue] = useState(hadoopClusterList.length ? 0 : 1);

  const getClusterInfo = () => {
    const url = '/api/cluster/info';

    axios.get(url)
      .then(response => {
        console.log(response);
        setHadoopClusterList(response.data.filter(e => (e.type === 0)));
        setSparkClusterList(response.data.filter(e => (e.type === 1)));
      })
      .catch(error => {
        alert(error.message);
        console.log(error);
      });
  };

  useEffect(() => {
    getClusterInfo();
  }, [hadoopClusterList, sparkClusterList]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Container maxWidth="lg">
      <Grid container direction={{ xs: "column", md: "row" }} spacing={3} my={3}>
        <Grid item xs md={4}>
          <Typography variant="h6" color="primary.main">User Info</Typography>
          <UserInfo />
        </Grid>
        {hadoopClusterList.length > 0 || sparkClusterList.length > 0 ?
          <Grid item xs md container direction="column" spacing={{ xs: 0, md: 3 }}>
            <Grid item xs>
              <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                  <Tab label="Hadoop" value={0} {...a11yProps(0)} />
                  <Tab label="Spark" value={1} {...a11yProps(1)} />
                </Tabs>
              </Box>
              <TabPanel value={value} index={0}>
                <Typography variant="h6" color="primary.main">Hadoop Cluster Info</Typography>
                <ClusterInfo cluster={HADOOP} clusterList={hadoopClusterList} />
              </TabPanel>
              <TabPanel value={value} index={1}>
                <Typography variant="h6" color="primary.main">Spark Cluster Info</Typography>
                <ClusterInfo cluster={SPARK} clusterList={sparkClusterList} />
              </TabPanel>
            </Grid>
          </Grid>
          :
          <Grid item xs md container direction="column" spacing={{ xs: 0, md: 3 }}>
            <Stack alignItems="center" mt={8}>
              <CircularProgress />
              <Typography m={2}>
                {'Loading Cluster Info...'}
              </Typography>
            </Stack>
          </Grid>
        }
      </Grid>
    </Container>
  );
}