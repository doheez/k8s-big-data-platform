import { Box, Button, Divider, List, ListItem, Stack, Typography, Grid } from "@mui/material";
import PodTable from "./PodTable";

export default function HadoopClusterInfo({ cluster }) {
  return (
    <Box sx={{ backgroundColor: "mypageBox.main", borderRadius: 1 }}>
      <List>
        <ListItem>
          <Stack>
            <Typography variant="subtitle2" color="text.secondary">Cluster ID</Typography>
            <Typography color="text.primary">test-{cluster}-cluster</Typography>
          </Stack>
        </ListItem>
        <Divider />
        <ListItem>
          <Stack>
            <Typography variant="subtitle2" color="text.secondary">Numbers of Clusters</Typography>
            <Typography color="text.primary">5</Typography>
          </Stack>
        </ListItem>
        <Divider />
        <ListItem>
          <Stack width="100%">
            <Typography variant="subtitle2" color="text.secondary">Pods Status</Typography>
            <PodTable />
          </Stack>
        </ListItem>
        <Divider />
        <ListItem>
          <Stack>
            <Typography variant="subtitle2" color="text.secondary">
              {cluster === 'hadoop' ? 'Size of Data' : 'Throughput'}
            </Typography>
            <Typography color="text.primary">그래프 삽입 예정</Typography>
          </Stack>
        </ListItem>
        <Divider />
        <ListItem>
          <Stack width="100%" rowGap={1}>
            <Typography variant="subtitle2" color="text.secondary">Cluster Management</Typography>
            <Grid container spacing={2}>
              <Grid item xs={6}><Button variant="contained" fullWidth>add cluster</Button></Grid>
              <Grid item xs={6}><Button variant="contained" fullWidth>delete cluster</Button></Grid>
            </Grid>
          </Stack>
        </ListItem>
      </List>
    </Box>
  );
}