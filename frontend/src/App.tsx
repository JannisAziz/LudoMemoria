import React from 'react';
import './App.css';
import ProfilePage from "./pages/ProfilePage";
import SearchPage from "./pages/SearchPage";

export default function App() {
    return (
        <div className="App">
            <header className="App-header">LudoMemoria [WIP]</header>
            <SearchPage/>
            <ProfilePage/>
        </div>
    );
}