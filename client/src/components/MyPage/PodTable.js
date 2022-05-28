import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box } from '@mui/material';
import { useState, useEffect } from 'react';
import PodDetailDialog from './PodDetailDialog';

export default function PodTable({ cluster, pods }) {
  const [open, setOpen] = useState(false);
  const [rows, setRows] = useState([]);
  const [columns, setColumns] = useState([]);

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
        onRowClick={() => setOpen(true)}
        components={{
          Toolbar: GridToolbar,
        }}
      />
      <PodDetailDialog open={open} setOpen={setOpen} cluster={cluster} />
    </Box>
  );
}