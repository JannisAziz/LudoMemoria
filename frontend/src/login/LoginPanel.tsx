import React from "react";
import SignInForm from "./SignInForm";
import SignUpForm from "./SignUpForm";
import {TabList, TabPanel, Tabs, Tab} from "react-tabs";

export default function LoginPanel() {
    return (
        <Tabs className={"LoginPanel"}>
            <TabList className={"TabList"}>
                <Tab className={"Tab"}>Sign In</Tab>
                <Tab className={"Tab"}>Sign Up</Tab>
            </TabList>
            <TabPanel children={<SignInForm/>}/>
            <TabPanel children={<SignUpForm/>}/>

            {/*<div>
                <GithubLoginButton align={"center"} onClick={() => onSocialLogin("github")}/>
                <GoogleLoginButton align={"center"} onClick={() => onSocialLogin("google")}/>
            </div>*/}
        </Tabs>
    );
}
