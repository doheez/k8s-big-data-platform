import { Box, Button, Stack, Typography } from "@mui/material";
import { ReactComponent as NaverLogo } from '../../images/naverLogo.svg';
import { ReactComponent as KakaoLogo } from '../../images/kakaoLogo.svg';
import { ReactComponent as GoogleLogo } from '../../images/googleLogo.svg';
import { ReactComponent as FacebookLogo } from '../../images/facebookLogo.svg';

export default function LoginSNS() {

  return (
    <Box mt={8}>
      <Typography variant="h6" align="center">
        Or Log in with SNS
      </Typography>
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
        <Button variant="contained" size="large" color="google">
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