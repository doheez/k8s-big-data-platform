import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      light: '#ae52d4',
      main: '#7b1fa2',
      dark: '#4a0072',
      contrastText: '#fff',
    },
    secondary: {
      light: '#ffff8b',
      main: '#ffee58',
      dark: '#c9bc1f',
      contrastText: '#000',
    },
    naver: {
      main: '#03C75A',
      contrastText: '#fff',
    },
    kakao: {
      main: '#FEE500',
      contrastText: '#000',
    },
    google: {
      main: '#fff',
      contrastText: '#000',
    },
    facebook: {
      main: '#4267B2',
      contrastText: '#fff',
    },
    mypageBox: {
      main: '#eeeeee'
    }
  },
});

export default theme;