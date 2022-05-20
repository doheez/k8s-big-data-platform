import { Container, Box } from "@mui/material";
import LoginEmail from "./LoginEmail";
import LoginSNS from "./LoginSNS";
import axios from 'axios';

export default function Login() {
  return (
    <Container maxWidth="sm">
      <Box
        my={7}
        p={8}
        sx={{ border: 1, borderRadius: 1, borderColor: 'primary.main' }}>
        <LoginEmail />
        <LoginSNS />
      </Box>
    </Container>
  );
}

export const chcekLoginStatusAsync = (setLoginStatus) => {
  const url = '/api/v1/user';
  const config = {
    maxRedirects: 0, // node.js에서 리디렉션 최대값을 정의한다. (기본값: 5)
    withCredentials: true // 자격 증명을 사용하여 사이트 간 액세스 제어 요청을 해야 하는지 여부를 나타낸다. (기본값: false)
  };

  axios.get(url, config)
    .then(response => {
      if (response.data && response.data.name !== null && response.data.name !== 'null') {
        setLoginStatus(true);
        console.log('사용자 정보를 받아왔습니다.');
        console.log(response);
      } else {
        setLoginStatus(false);
        console.log('사용자 정보를 받아오지 못했습니다.');
        console.log(response);
      }
    })
    .catch(error => {
      setLoginStatus(false);
      console.log('사용자 정보 API 요청을 실패했습니다.');
      console.log(error);
    });
};