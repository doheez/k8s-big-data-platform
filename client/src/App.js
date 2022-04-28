import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

import { ThemeProvider } from '@mui/material';
import theme from './theme/Theme';
import ResponsiveAppBar from './components/AppBar/ResponsiveAppBar';
import Banner from './components/Banner/Banner';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <ResponsiveAppBar />
      <Banner />
    </ThemeProvider>
  );
}

export default App;