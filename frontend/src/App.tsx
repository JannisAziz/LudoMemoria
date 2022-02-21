import React from 'react';
import Navigation from "./components/Navigation";
import HomePage from "./components/HomePage";
import UserPage from "./users/UserPage";
import CatalogPage from "./games/CatalogPage";
import AboutPage from "./components/AboutPage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import GamePage from "./games/GamePage";
import "./styles/Styles.scss";

export default function App() {
    return (
        <div className={"App"}>
            <BrowserRouter>
                <Navigation/>
                <Routes>
                    <Route path="/" element={<HomePage />}/>
                    <Route path="/profile" element={<UserPage />}/>
                    <Route path="/catalog" element={<CatalogPage />}/>
                    <Route path="/catalog/:search" element={<CatalogPage />}/>
                    <Route path="/about" element={<AboutPage />}/>
                    <Route path="/games/:gameId" element={<GamePage />}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}