import { Box } from '@mui/material';

export function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`cluster-tabpanel-${index}`}
      aria-labelledby={`cluster-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3} px={{ xs: 0, md: 3 }}>
          {children}
        </Box>
      )}
    </div>
  );
}
export function a11yProps(index) {
  return {
    id: `clueter-tab-${index}`,
    'aria-controls': `cluster-tabpanel-${index}`,
  };
}
