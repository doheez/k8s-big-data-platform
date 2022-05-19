import { Box, Button, Stack, Typography, styled, Divider } from "@mui/material";
import { ReactComponent as NaverLogo } from '../../images/naverLogo.svg';
import { ReactComponent as KakaoLogo } from '../../images/kakaoLogo.svg';
import { ReactComponent as GoogleLogo } from '../../images/googleLogo.svg';
import { ReactComponent as FacebookLogo } from '../../images/facebookLogo.svg';

const Root = styled('div')(({ theme }) => ({
  width: '100%',
  ...theme.typography.body2,
  '& > :not(style) + :not(style)': {
    marginTop: theme.spacing(2),
  },
}));

export default function LoginSNS() {
  const API_BASE_URL = 'http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080';

  const handleClickGoogle = () => {
    window.location.href = API_BASE_URL + '/oauth2/authorization/google';
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