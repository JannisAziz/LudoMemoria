import React from "react";
import Login from "../components/Login";
import Header from "../components/utils/Header";

export default function HomePage() {
    return (
        <div>
            Home
            <Header/>
            <Login/>
        </div>
    );
}