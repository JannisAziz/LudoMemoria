import React from "react";
import {TestGames, TestReviews, TestSavedGames, TestUser} from "../models/TESTDATA";
import SavedGamesList from "../components/games/SavedGamesList";
import GamesList from "../components/games/GamesList";
import ReviewList from "../components/ReviewList";

export default function ProfilePage() {

    const user = TestUser();
    user.savedGames = TestSavedGames();
    user.wishlistGames = TestGames();
    user.reviews = TestReviews();

    return (
        <div>
            Profile
            <div>
                <img src={"/background.jpg"} alt={"backgroundImg"} height={"auto"} width={"600px"}/>
                <img src={"/avatar.png"} alt={"avatarImg"}/>
                <div>
                    User details - 
                    {user.description ? "Description!" : "Nothing here..."}
                </div>
                <div>
                    Social Icons - 
                    {user.socialLinks ? "Icons!" : "Nothing here..."}
                </div>
                <div>
                    SavedGames - 
                    {user.savedGames ? (<SavedGamesList games={user.savedGames}/>) : "Nothing here..."}
                </div>
                <div>
                    Wishlist - 
                    {user.wishlistGames ? (<GamesList games={user.wishlistGames}/>) : "Nothing here..."}
                </div>
                <div>
                    Reviews - 
                    {user.reviews ? (<ReviewList reviews={user.reviews}/>) : "Nothing here..."}
                </div>


            </div>
        </div>
    );
}