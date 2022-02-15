import React from "react";
import SavedGamesList from "../games/SavedGamesList";
import ReviewList from "../games/ReviewList";
import {NavLink} from "react-router-dom";
import {getLoggedInUser} from "./UserService";
import {Tab, TabList, TabPanel, Tabs} from "react-tabs";

export default function ProfilePage() {

    const user = getLoggedInUser()

    return (
        <div>
            {user.username ? (
                    <div className={"ProfilePanel"}>
                        <h2>Hello, {user.username}</h2>

                        <Tabs className={"TabsPanel"}>
                            <TabList className={"TabList"}>
                                <Tab className={"Tab"}>Library</Tab>
                                <Tab className={"Tab"}>Reviews</Tab>
                            </TabList>
                            <TabPanel children={<SavedGamesList games={user.savedGames}/>}/>
                            <TabPanel children={<ReviewList reviews={user.reviews}/>}/>
                        </Tabs>
                    </div>
                ) :
                (<LoginRedirect/>)
            }
        </div>
    );
}

function LoginRedirect() {
    return (
        <div>
            <p>Not Logged in</p>
            <NavLink to={"/"}>Return to home </NavLink>
        </div>
    )
}