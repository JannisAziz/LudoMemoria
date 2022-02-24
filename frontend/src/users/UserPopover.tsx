import React, {MouseEventHandler, useState} from "react";
import {Avatar, Button, Popover} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {getLoggedInUser} from "./UserService";
import {signOut} from "../login/LoginService";

export default function UserPopover() {
    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null)
    const open = Boolean(anchorEl)
    const id = open ? 'simple-popover' : undefined

    const onOpen: MouseEventHandler<HTMLButtonElement> = e => setAnchorEl(e.currentTarget)
    const onClose = () => setAnchorEl(null)

    const nav = useNavigate()

    const onGoToProfile: MouseEventHandler<HTMLButtonElement> = (e) => {
        onClose()
        nav("/profile")
    }

    const onSignOut: MouseEventHandler<HTMLButtonElement> = (e) => {
        onClose()
        signOut()
        nav("/")
    }

    return (
        <div className={"UserPopoverPanel"}>
            <Button aria-describedby={id} variant={"text"} onClick={onOpen} sx={{"fontSize": "large"}}>
                <Avatar sx={{ width: 56, height: 56 }}>{getLoggedInUser().username?.substring(0,1).toUpperCase() || "..."}</Avatar>
            </Button>
            <Popover className={"UserPopover"}
                     id={id}
                     open={open} onClose={onClose}
                     anchorEl={anchorEl} anchorOrigin={{vertical: 'top', horizontal: 'left',}}>
                <Button onClick={onGoToProfile}>Go to profile</Button>
                <Button onClick={onSignOut}>Logout</Button>
            </Popover>
        </div>
    )
}
