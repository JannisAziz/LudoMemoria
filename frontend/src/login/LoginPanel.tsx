import React, {useState} from "react";
import SignInForm from "./SignInForm";
import SignUpForm from "./SignUpForm";
import {Tab} from "@mui/material";

export default function LoginPanel() {

    const [tabToggle, setTabToggle] = useState(true)

    return (
        <div className={"LoginPanel"}>
            <div className={"Tabs"}>
                <Tab className={"Tab"} label={"Sign In"} onClick={() => setTabToggle(true)}/>
                <Tab className={"Tab"} label={"Sign Up"} onClick={() => setTabToggle(false)}/>
            </div>
            {tabToggle ?
                (<div children={<SignInForm onSubmit={() => {}}/>}/>) :
                (<div children={<SignUpForm onSubmit={() => {}}/>}/>)
            }
        </div>
    );
}
