import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

import ResponsiveAppBar from './components/AppBar/ResponsiveAppBar';
import Banner from './components/Banner/Banner';
import { Box } from '@mui/material';

function App() {
  return (
    <Box>
      <ResponsiveAppBar />
      <Banner />
    </Box>
  );
}

export default App;
