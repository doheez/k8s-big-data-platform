import { Grid, Typography } from "@mui/material"
import "./CreatingCluster.css";

export default function CreatingClusterText() {
  return (
    <Grid container>
      <Grid item mr={3}>
        <div className="divider"></div>
      </Grid>
      <Grid item sx={{ display: 'flex', alignItems: 'center' }}>
        <Typography variant="subtitle1" component="div">
          To make clusters, please select the option.<br />
          After click the button, you can enter the number of clusters to configure.
        </Typography>
      </Grid>
    </Grid>
  );
}