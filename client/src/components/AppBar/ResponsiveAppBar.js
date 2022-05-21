import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import { Link as RouterLink } from 'react-router-dom';

const pages = ['Main', 'My Page', 'Log In'];
const links = ['/', '/mypage', '/login']

const ResponsiveAppBar = ({ token, setToken }) => {
  const [anchorElNav, setAnchorElNav] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = (index) => {
    // 로그아웃 버튼일 경우
    if (index === 2 && token !== undefined) {
      handleLogOut();
    }
    setAnchorElNav(null);
  };

  const handleLogOut = () => {
    window.localStorage.clear();
    setToken(undefined);
    alert('Success in log out!');
  };

  return (
    <AppBar position="sticky" sx={{ boxShadow: 0, borderBottom: "1px solid", borderBottomColor: "primary.light" }}>
      <Container maxWidth="lg">
        <Toolbar disableGutters>
          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              {pages.map((page, index) => (
                <MenuItem
                  key={page}
                  onClick={() => handleCloseNavMenu(index)}
                  component={RouterLink}
                  to={links[index]}
                >
                  <Typography textAlign="center">{index === 2 && token !== undefined ? 'Log Out' : page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }} />
          <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
            {pages.map((page, index) => (
              <Button
                key={page}
                onClick={() => handleCloseNavMenu(index)}
                sx={{ my: 1, color: 'white', display: 'block', textAlign: 'center' }}
                component={RouterLink}
                to={links[index]}
              >
                {index === 2 && token !== undefined ? 'Log Out' : page}
              </Button>
            ))}
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
};
export default ResponsiveAppBar;