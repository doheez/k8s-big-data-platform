import * as React from 'react';
import { Box, Button, Grid, TextField, Stack, Typography } from '@mui/material';
import { useState } from 'react';
import EmailListItem from './EmailListItem';
import './InviteUser.css';
import axios from 'axios'
import ClusterSelect from './ClusterSelect';

export default function InviteUser({ clusterNameList }) {
  const [email, setEmail] = useState('');
  const [emailArray, setEmailArray] = useState([]);
  const [isEmail, setIsEmail] = useState(true);
  const [alreadyAdded, setAlreadyAdded] = useState(false);
  const [alreadyInvited, setAlreadyInvited] = useState(false);
  const [selectedClusterName, setSelectedClusterName] = useState('');

  const checkEmail = (email) => {
    const emailRegex =
      /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    const result = emailRegex.test(email);
    setIsEmail(result);
    return result;
  };

  const checkAlreadyAdded = (email) => {
    const added = emailArray.some(i => i === email);
    setAlreadyAdded(added);
    return added;
  };

  const checkAlreadyInvited = (email) => {
    // const invited = project.visitor.some(visitor => visitor.email === email);
    // setAlreadyInvited(invited);
    // return invited;
    return alreadyInvited;
  };

  const handleAddClick = () => {
    if (checkEmail(email) && !checkAlreadyAdded(email) && !checkAlreadyInvited(email)) {
      const newEmailArray = emailArray.concat(email);
      setEmailArray(newEmailArray);
      setEmail('');
    }
  };

  const handleInviteClick = () => {
    const url = '/api/cluster/user';
    const data = {
      clusterName: selectedClusterName,
      emails: emailArray
    };
    
    console.log(data);
    
    axios.post(url, data)
      .then(response => {
        alert('Invited user successfully!');
        console.log(response);
      }).catch(error => {
        // if (error.response) {
        //   alert(error.response.data.message + '\nAccounts Failed to Invite: ' + error.response.data.failList);
        //   console.log(error.response.data);
        // } else {
        //   alert(error.message);
        //   console.log(error);
        // }
        alert(error.messate);
        console.log(error);
      }).finally(() => {
        setEmailArray([]);
        setEmail('');
        setIsEmail(true);
        setAlreadyAdded(false);
        setAlreadyInvited(false);
        setSelectedClusterName('');
        // window.location.reload();
      });
  };

  return (
    <Box p={2} sx={{ backgroundColor: "mypageBox.main", borderRadius: 1 }}>
      <ClusterSelect clusterNameList={clusterNameList} selectedClusterName={selectedClusterName} setSelectedClusterName={setSelectedClusterName} />
      <Typography variant="subtitle2" gutterBottom component="div" color="#014361" sx={{ mt: 3 }}>
        <span className="invite-divider" />Add Account by User Email
      </Typography>
      <Grid container columnSpacing={1}>
        <Grid item xs>
          <TextField
            autoComplete="off"
            label="Email"
            size="small"
            fullWidth
            onChange={e => { setEmail(e.target.value) }}
            value={email}
            error={(!isEmail && true) || (alreadyAdded && true) || (alreadyInvited && true)}
            helperText={(!isEmail && "Not a valid email format.") || (alreadyAdded && "This account has already added.")
              || (alreadyInvited && "This account has already invited.")}
          />
        </Grid>
        <Grid item xs='auto'>
          <Button onClick={handleAddClick} variant="outlined" sx={{ py: '7.75px' }} size="small">Add</Button>
        </Grid>
      </Grid>
      {emailArray.length > 0 && (
        <Typography variant="subtitle2" gutterBottom component="div" color="#014361" sx={{ mt: 3 }}>
          <span className="invite-divider" />User List to Invite
        </Typography>
      )}
      <Stack>
        {emailArray.map((email, index) => {
          return (
            <EmailListItem key={email} email={email} index={index} emailArray={emailArray} setEmailArray={setEmailArray}></EmailListItem>
          );
        })}
      </Stack>
      <Button onClick={handleInviteClick} disabled={emailArray.length === 0} fullWidth variant="contained" sx={{ mt: 2 }}>Invite</Button>
    </Box>
  );
}