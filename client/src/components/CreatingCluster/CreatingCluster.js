import { Container, Grid } from "@mui/material";
import CreatingClusterText from "./CreatingClusterText";
import CreatingClusterButton from "./CreatingClusterButton";

export default function CreatingCluster() {
  return (
      <Container maxWidth="lg">
        <Grid container direction="column">
          <Grid item my={5}>
            <CreatingClusterText />
          </Grid>
          <Grid item>
            <CreatingClusterButton />
          </Grid>
        </Grid>
      </Container>
  );
}