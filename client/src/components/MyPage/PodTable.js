import { DataGrid } from '@mui/x-data-grid';
import { Box } from '@mui/material';
import { useState } from 'react';
import PodDetailDialog from './PodDetailDialog';

export default function PodTable({ cluster }) {
  const [open, setOpen] = useState(false);

  // 임시 데이터
  const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'name', headerName: 'Name', width: 180, },
    { field: 'ready', headerName: 'Ready', width: 90, },
    { field: 'status', headerName: 'Status', width: 130, },
    { field: 'age', headerName: 'Age', width: 110, },
  ];

  const rows = [
    { id: 1, name: 'find-animal-sdfk4r', ready: '1/1', status: 'RUNNING', age: '78ms' },
    { id: 2, name: 'find-animal-sdfk4f', ready: '1/1', status: 'RUNNING', age: '78ms' },
    { id: 3, name: 'find-animal-sdfk4t', ready: '1/1', status: 'RUNNING', age: '78ms' },
    { id: 4, name: 'find-animal-sdfk4t', ready: '1/1', status: 'RUNNING', age: '78ms' },
    { id: 5, name: 'find-animal-sdfk4t', ready: '1/1', status: 'RUNNING', age: '78ms' },
  ];

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
      />
      <PodDetailDialog open={open} setOpen={setOpen} cluster={cluster} />
    </Box>
  );
}