import React from "react";
import {NavLink} from "react-router-dom";

export default function Navigation() {
    return (
        <nav>
            <NavLink to={"/"}>Home</NavLink>
            <NavLink to={"/profile"}>Profile</NavLink>
            <NavLink to={"/catalog"}>Catalog</NavLink>
            <NavLink to={"/about"}>About</NavLink>
        </nav>
    );
}