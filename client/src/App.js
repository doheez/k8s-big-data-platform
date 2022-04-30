import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

import { ThemeProvider } from '@mui/material';
import theme from './theme/Theme';
import ResponsiveAppBar from './components/AppBar/ResponsiveAppBar';
import Banner from './components/Banner/Banner';
import CreatingCluster from './components/CreatingCluster/CreatingCluster';
import Login from './components/Login/Login';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <ResponsiveAppBar />
      {/* <Banner />
      <CreatingCluster /> */}
      <Login />
    </ThemeProvider>
  );
}

export default App;