import UserInfo from './UserInfo';
import ClusterInfo from './ClusterInfo';
import { Container, Grid, Typography } from '@mui/material';
import { useState, useEffect } from "react";
import axios from 'axios';

const HADOOP = 'hadoop', SPARK = 'spark';

export default function MyPage() {
  const [name, setName] = useState();
  const [email, setEmail] = useState();
  const [hadoopClusterList, setHadoopClusterList] = useState([]);
  const [sparkClusterList, setSparkClusterList] = useState([]);

  // const getClusterInfo = () => {
  //   const url = '/api/cluster/info';

  //   axios.get(url)
  //     .then(response => {
  //       console.log(response);
  //       setHadoopClusterList(response.data.clusters.filter(e => (e.type === 0)));
  //       setSparkClusterList(response.data.clusters.filter(e => (e.type === 1)));
  //     })
  //     .catch(error => {
  //       console.log(error);
  //       alert(error.message);
  //     });
  // };

  useEffect(() => {
    // getClusterInfo();
    setHadoopClusterList(testData.clusters.filter(e => (e.type === "0")));
    setSparkClusterList(testData.clusters.filter(e => (e.type === "1")));
  }, []);

  return (
    <Container maxWidth="lg">
      <Grid container direction={{ xs: "column", md: "row" }} spacing={3} my={3}>
        <Grid item xs md={4}>
          <Typography variant="h6" color="primary.main">User Info</Typography>
          <UserInfo name={name} setName={setName} email={email} setEmail={setEmail} />
        </Grid>
        <Grid item xs md container direction="column" spacing={{ xs: 0, md: 3 }}>
          <Grid item xs>
            <Typography variant="h6" color="primary.main">Hadoop Cluster Info</Typography>
            <ClusterInfo cluster={HADOOP} name={name} clusterList={hadoopClusterList} />
          </Grid>
          <Grid item xs>
            <Typography variant="h6" color="primary.main">Spark Cluster Info</Typography>
            <ClusterInfo cluster={SPARK} name={name} clusterList={sparkClusterList} />
          </Grid>
        </Grid>
      </Grid>
    </Container>
  );
}

const testData = {
  "clusters": [
    {
      "type": "0",  //[0 : hadoop, 1 : spark]
      "clusterName": "하둡 클러스터 1",
      "podInfos": [
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
      ]
    },
    {
      "type": "0",  //[0 : hadoop, 1 : spark]
      "clusterName": "하둡 클러스터 2",
      "podInfos": [
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
      ]
    },
    {
      "type": "0",  //[0 : hadoop, 1 : spark]
      "clusterName": "하둡 클러스터 3",
      "podInfos": [
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
      ]
    },
    {
      "type": "1",  //[0 : hadoop, 1 : spark]
      "clusterName": "스파크 클러스터 1",
      "podInfos": [
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
      ]
    },
    {
      "type": "1",  //[0 : hadoop, 1 : spark]
      "clusterName": "스파크 클러스터 2",
      "podInfos": [
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
        {
          "nodeName": "example-node",
          "nodeIP": "127.0.0.1",
          "status": "RUNNING"
        },
      ]
    },
  ]
};