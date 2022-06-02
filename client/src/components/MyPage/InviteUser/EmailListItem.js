import * as React from 'react';
import { Alert, Divider, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import MailOutlineIcon from '@mui/icons-material/MailOutline';

export default function EmailListItem(props) {
  const { email, index, emailArray, setEmailArray } = props;

  const getRadius = () => {
    if (index === 0 && index === emailArray.length - 1) {
      return '4px 4px 4px 4px';
    } else if (index === 0 && index !== emailArray.length - 1) {
      return '4px 4px 0px 0px';
    } else if (index === emailArray.length - 1) {
      return '0px 0px 4px 4px';
    } else {
      return '0px 0px 0px 0px';
    }
  };

  return (
    <React.Fragment>
      <Alert
        severity="info"
        icon={<MailOutlineIcon fontSize="inherit" />}
        action={
          <IconButton
            aria-label="close"
            color="inherit"
            size="small"
            onClick={() => {
              setEmailArray(emailArray.filter(i => i !== email));
            }}
          >
            <CloseIcon fontSize="inherit" />
          </IconButton>
        }
        sx={{ borderRadius: getRadius() }}
      >
        {email}
      </Alert>
      {index !== emailArray.length - 1 && <Divider color="#d9f3ff" />}
    </React.Fragment>
  );
}