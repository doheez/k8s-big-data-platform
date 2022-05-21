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
import MyPage from './components/MyPage/MyPage';
import { chcekLoginStatusAsync } from './components/Login/Login';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Fragment, useState } from 'react';

function App() {
  const [loginStatus, setLoginStatus] = useState();

  if (loginStatus === undefined) {
    // Oauth 도메인 관련 문제로 구글 로그인을 일시 비활성화합니다.
    // chcekLoginStatusAsync(setLoginStatus);
    
    console.log('사용자 정보를 확인중입니다..');
  }
  else if (loginStatus) {
    console.log('loginStatus: ' + loginStatus);
  }
  else if (loginStatus === false) {
    console.log('loginStatus: ' + loginStatus);
  }
  else {
    alert("로그인 확인 중 오류가 발생했습니다.");
  }

  return (
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <ResponsiveAppBar />
        <Routes>
          <Route path="/" element={<Fragment><Banner /><CreatingCluster /></Fragment>}></Route>
          <Route path="/mypage" element={<MyPage />}></Route>
          <Route path="/login" element={<Login />}></Route>
          {/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는 경우 처리 */}
          <Route path="*" element={<Fragment><Banner /><CreatingCluster /></Fragment>}></Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;