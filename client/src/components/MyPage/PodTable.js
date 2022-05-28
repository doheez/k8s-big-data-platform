import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box } from '@mui/material';
import { useState, useEffect } from 'react';
import PodDetailDialog from './PodDetailDialog';
import axios from 'axios';

export default function PodTable({ cluster, pods, clusterName }) {
  const [open, setOpen] = useState(false);
  const [rows, setRows] = useState([]);
  const [columns, setColumns] = useState([]);
  const [podDetails, setPodDetails] = useState();

  const getColumns = () => {
    if (pods.length > 0) {
      const keys = Object.keys(pods[0]);
      const columns = keys.map((key, index) => {
        return { field: key, headerName: key, width: 200 };
      });
      return columns;
    }
  };

  const getRows = () => {
    return pods.map((row, index) => {
      return { ...row, id: index };
    });
  };

  const getPodDetails = (podName) => {
    const url = '/api/cluster/detail';
    const data = {
      clusterName: clusterName,
      podName: podName
    };

    axios.post(url, data)
      .then(response => {
        setPodDetails(response);
        console.log(response);
      })
      .catch(error => {
        if (error.response.data) {
          alert(error.response.data);
        } else {
          alert(error.message);
        }
        console.log(error);
      });
  };

  const handleRowClick = (params) => {
    // getPodDetails(params.row.nodeName);
    setOpen(true);
  };

  useEffect(() => {
    setColumns(getColumns());
    setRows(getRows());
  }, [pods]);

  return (
    <Box width="100%" height={350} my={1}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        sx={{
          border: 2,
          borderColor: 'primary.light',
          '& .MuiDataGrid-cell:hover': {
            color: 'primary.main',
          },
        }}
        onRowClick={handleRowClick}
        components={{
          Toolbar: GridToolbar,
        }}
      />
      <PodDetailDialog open={open} setOpen={setOpen} cluster={cluster} podDetails={testData} />
    </Box>
  );
}

const testData = {
  "name": "example-hadoopservice-hadoop-slave-0",
  "namespace": "hadoop",
  "nodeName": "gke-cluster-1-default-pool-bd9844b9-f2lf",
  "nodeIP": "127.0.0.1",
  "startTime": "Thu, 26 May 2022 12:58:29",
  "status": "RUNNING",
  "podIP": "10.4.0.104"
};