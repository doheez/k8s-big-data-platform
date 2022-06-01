import { Box, Button, Stack, Typography, styled, Divider } from "@mui/material";
import { ReactComponent as NaverLogo } from '../../images/naverLogo.svg';
import { ReactComponent as KakaoLogo } from '../../images/kakaoLogo.svg';
import { ReactComponent as GoogleLogo } from '../../images/googleLogo.svg';
import { ReactComponent as FacebookLogo } from '../../images/facebookLogo.svg';
import axios from "axios";

const Root = styled('div')(({ theme }) => ({
  width: '100%',
  ...theme.typography.body2,
  '& > :not(style) + :not(style)': {
    marginTop: theme.spacing(2),
  },
}));

export default function LoginSNS() {
  const AWS_API_BASE_URL = 'http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080';
  const LOCAL_API_BASE_URL = 'http://localhost:8080';

  const handleClickGoogle = () => {
    window.location.href = AWS_API_BASE_URL + '/oauth2/authorization/google';
  };

  return (
    <Box mt={8}>
      <Root>
        <Divider>
          <Typography variant="subtitle1" align="center">
            Or Log in with SNS
          </Typography>
        </Divider>
      </Root>
      <Stack spacing={1.5} mt={3}>
        <Button variant="contained" size="large" color="naver">
          <NaverLogo width="30px" />
          <Box width="100%">
            Login with Naver
          </Box>
        </Button>
        <Button variant="contained" size="large" color="kakao">
          <KakaoLogo width="30px" />
          <Box width="100%">
            Login with Kakao
          </Box>
        </Button>
        <Button variant="contained" size="large" color="google" onClick={() => handleClickGoogle()}>
          <GoogleLogo width="30px" />
          <Box width="100%">
            Login with Google
          </Box>
        </Button>
        <Button variant="contained" size="large" color="facebook">
          <FacebookLogo width="30px" />
          <Box width="100%">
            Login with Facebook
          </Box>
        </Button>
      </Stack>
    </Box>
  );
}

// 로그인 상태를 체크하고 구글 로그인 상태 정보를 받아오는 함수. (Deprecated)
export const checkLoginStatusAsync = (setLoginStatus) => {
  const url = '/api/v1/user';
  const config = {
    maxRedirects: 0 // node.js에서 리디렉션 최대값을 정의한다. (기본값: 5)
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