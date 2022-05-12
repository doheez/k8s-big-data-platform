import { Box, Typography } from "@mui/material"
import "./CreatingCluster.css";

export default function CreatingClusterText() {
  return (
      <Box sx={{ display: 'flex', alignItems: 'center' }}>
        <span className="divider"></span>
        <Typography variant="subtitle1" component="div">
          To make clusters, please select the option.<br />
          After click the button, you can enter the number of clusters to configure.
        </Typography>
      </Box>
  );
}