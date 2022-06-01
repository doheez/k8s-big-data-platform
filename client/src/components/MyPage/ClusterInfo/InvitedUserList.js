import { Avatar, AvatarGroup, Tooltip } from "@mui/material";
import { stringAvatar } from "../Utils/Utils";

const InvitedUserList = ({ userList }) => {

    return (
        <AvatarGroup max={10}>
            {userList.map(user =>
                <Tooltip title={`${user.name} (${user.email})`} key={user.email}>
                    <Avatar {...stringAvatar(user.name)} />
                </Tooltip>
            )}
        </AvatarGroup>
    );
}

export default InvitedUserList;