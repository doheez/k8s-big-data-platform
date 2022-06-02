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
import SignUp from './components/SignUp/SignUp';
import MyPage from './components/MyPage/MyPage';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Fragment, useState, useEffect } from 'react';
import { onSilentRefresh } from './components/Login/LoginEmail';

function App() {
  const [token, setToken] = useState();

  useEffect(() => {
    onSilentRefresh(setToken);
  }, []);

  return (
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <ResponsiveAppBar token={token} setToken={setToken} />
        {token === undefined
          ?
          <Routes>
            <Route path="/login" element={<Login setToken={setToken} />}></Route>
            <Route path="/signup" element={<SignUp />}></Route>
            {/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는 경우 처리 */}
            <Route path="*" element={<Login setToken={setToken} />}></Route>
          </Routes>
          :
          <Routes>
            <Route path="/" element={<Fragment><Banner /><CreatingCluster /></Fragment>}></Route>
            <Route path="/mypage" element={<MyPage />}></Route>
            {/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는 경우 처리 */}
            <Route path="*" element={<Fragment><Banner /><CreatingCluster /></Fragment>}></Route>
          </Routes>
        }
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;