import React from 'react';
import './App.css';
import Navigation from "./components/utils/Navigation";
import HomePage from "./pages/HomePage";
import ProfilePage from "./pages/ProfilePage";
import CatalogPage from "./pages/CatalogPage";
import AboutPage from "./pages/AboutPage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Header from "./components/utils/Header";

export default function App() {
    return (
        <div className={"App"}>
            <BrowserRouter>
                <Header/>
                <Navigation/>
                <Routes>
                    <Route path="/" element={<HomePage />}/>
                    <Route path="/profile" element={<ProfilePage />}/>
                    <Route path="/catalog" element={<CatalogPage />}/>
                    <Route path="/about" element={<AboutPage />}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}