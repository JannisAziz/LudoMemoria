import React, {useEffect} from "react";
import LoginPanel from "../login/LoginPanel";
import {getLoggedInUser} from "../users/UserService";
import {useNavigate} from "react-router-dom";

export default function HomePage() {

    const nav = useNavigate();

    useEffect(() => {
        const loggedInUser = getLoggedInUser()
        if (loggedInUser?.id) {
            alert("already logged in")
            nav("/profile")
        }
    }, []);

    return (
        <div>
            <LoginPanel/>
        </div>
    );
}