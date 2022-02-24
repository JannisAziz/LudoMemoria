import React, {MouseEventHandler, useState} from "react";
import {Button, Popover, Tab} from "@mui/material";
import {AccountBox} from "@mui/icons-material";
import SignInForm from "./SignInForm";
import SignUpForm from "./SignUpForm";

export default function LoginPopover() {
    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null)
    const open = Boolean(anchorEl)
    const id = open ? 'simple-popover' : undefined

    const onOpen: MouseEventHandler<HTMLButtonElement> = e => setAnchorEl(e.currentTarget)
    const onClose = () => setAnchorEl(null)

    const [tabToggle, setTabToggle] = useState(true)

    return (
        <div className={"LoginPanel"}>
            <Button aria-describedby={id} variant="contained" onClick={onOpen} sx={{"fontSize": "large"}}><AccountBox fontSize={"large"} fontStyle={"bold"}/><div className={"PanelText"}>Login</div></Button>
            <Popover className={"PopoverPopover"}
                     id={id}
                     open={open} onClose={onClose}
                     anchorEl={anchorEl} anchorOrigin={{vertical: 'top', horizontal: 'left',}}>
                <div className={"PopoverTabs"}>
                    <Tab className={"Tab"} label={"Sign In"} onClick={() => setTabToggle(true)}/>
                    <Tab className={"Tab"} label={"Sign Up"} onClick={() => setTabToggle(false)}/>
                </div>
                {tabToggle ?
                    (<div children={<SignInForm onSubmit={() => setAnchorEl(null)}/>}/>) :
                    (<div children={<SignUpForm onSubmit={() => setAnchorEl(null)}/>}/>)
                }
            </Popover>
        </div>
    )
}
