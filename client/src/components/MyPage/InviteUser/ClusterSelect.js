import { Typography, Box, FormControl, InputLabel, Select, MenuItem } from "@mui/material";

export default function ClusterSelect({ clusterList, selectedCluster, setSelectedCluster }) {
  const handleChange = (event) => {
    setSelectedCluster(event.target.value);
  };

  return (
    <Box>
      <Typography variant="subtitle2" gutterBottom component="div" color="#014361">
        <span className="invite-divider" />Select Cluster
      </Typography>
      <FormControl fullWidth size="small">
        <InputLabel id="cluster-select-label">Cluster List</InputLabel>
        <Select
          labelId="cluster-select-label"
          id="cluster-select"
          value={selectedCluster}
          label="Cluster List"
          onChange={handleChange}
        >
          {clusterList.map(cluster => <MenuItem key={cluster} value={cluster}>{cluster}</MenuItem>)}
        </Select>
      </FormControl>
    </Box>
  );
}