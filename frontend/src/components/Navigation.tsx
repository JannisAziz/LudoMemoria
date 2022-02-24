import React from "react";
import {useNavigate} from "react-router-dom";
import LoginPopover from "../login/LoginPopover";
import {Button} from "@mui/material";
import SearchForm from "./SearchForm";
import {Home} from "@mui/icons-material";
import {getLoggedInToken} from "../login/LoginService";
import UserPopover from "../users/UserPopover";

export default function Navigation() {
    const nav = useNavigate()

    const isLoggedIn = getLoggedInToken().length > 0

    const onSearch = (search: string) => {
        nav(`/search=${search}`)
    }

    return (
        <nav className={"NavHeader"}>
            <Button className={"NavLogo"} variant={"contained"} onClick={() => nav(`/`)} sx={{"fontSize": "large", "background": "hotpink"}}><Home fontSize={"large"} fontStyle={"bold"}/><div>Ludo Memoria</div></Button>
            <SearchForm onSearch={onSearch}/>
            {isLoggedIn ?
                <UserPopover/>:
                <LoginPopover/>
            }
        </nav>
    );
}
