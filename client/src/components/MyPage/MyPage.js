import UserInfo from './UserInfo';
import ClusterInfo from './ClusterInfo';
import { Container, Grid, Typography, Stack, CircularProgress, Tabs, Tab, Box } from '@mui/material';
import { useState, useEffect } from "react";
import axios from 'axios';
import { a11yProps, TabPanel } from './TabPanel';
import InviteUser from './InviteUser/InviteUser';

const HADOOP = 'hadoop', SPARK = 'spark';

export default function MyPage() {
  const [clusterNameList, setClusterNameList] = useState([]);
  const [hadoopClusterList, setHadoopClusterList] = useState([]);
  const [sparkClusterList, setSparkClusterList] = useState([]);
  const [value, setValue] = useState(0);
  const [checkEmpty, setEmpty] = useState(true);

  const getClusterInfo = () => {
    const url = '/api/cluster/info';

    axios.get(url)
      .then(response => {
        console.log(response);
        setClusterNameList(response.data.map(cluster => cluster.clusterName));
        setHadoopClusterList(response.data.filter(e => (e.type === 0)));
        setSparkClusterList(response.data.filter(e => (e.type === 1)));

        if (checkEmpty) {
          if (hadoopClusterList.length > 0 || sparkClusterList.length > 0) {
            setEmpty(false);
            // Hadoop은 없고 Spark 클러스터 정보만 있을 경우 Spark 탭을 보여준다.
            if (hadoopClusterList.length === 0) {
              setValue(1);
            }
          }
        }
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
        <Grid item xs md={4} container direction="column" rowSpacing={3}>
          <Grid item xs="auto">
            <Typography variant="h6" color="primary.main">User Info</Typography>
            <UserInfo />
          </Grid>
          {!checkEmpty &&
            <Grid item xs="auto">
              <Typography variant="h6" color="primary.main">Invite User to Cluster</Typography>
              <InviteUser clusterNameList={clusterNameList} />
            </Grid>
          }
        </Grid>
        {!checkEmpty ?
          <Grid item xs md container direction="column" spacing={{ xs: 0, md: 3 }}>
            <Grid item xs>
              <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="cluster tabs">
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