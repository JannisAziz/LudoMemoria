import React, {useEffect, useState} from "react";
import {NavLink, useParams} from "react-router-dom";
import {getLoggedInUser, getUserById, retrieveCurrentUser} from "./UserService";
import {findReviewsByUserId} from "../reviews/ReviewService";
import {Review} from "../reviews/Review";
import {Tab} from "@mui/material";
import {User} from "./User";
import {getLoggedInToken} from "../login/LoginService";
import {SavedGame} from "../games/SavedGame";
import {findSavedGamesByUserId} from "../games/SavedGameService";
import {ReviewsListUserPage} from "../reviews/ReviewComponents";
import SavedGamesList from "../games/SavedGamesList";

export default function UserPage() {

    const {userId} = useParams()

    const [thisUser, setThisUser] = useState<User>()
    const [savedGames, setSavedGames] = useState<SavedGame[]>()
    const [reviews, setReviews] = useState<Review[]>()
    const [tabToggle, setTabToggle] = useState(true)

    useEffect(() => {
        if (userId) {
            const isOwner = getLoggedInUser().id === userId

            const response = getLoggedInToken() && isOwner? retrieveCurrentUser() : getUserById(userId)
            response.then((user) => {
                        setThisUser(user)
                        updateUserData(user.id)
                    })
                    .catch(err => "Error retrieving user " + err.message)
        } else if (getLoggedInToken()) {
            retrieveCurrentUser()
                .then((user) => {
                setThisUser(user)
                updateUserData(user.id)
            })
                .catch(err => "Error retrieving user " + err.message)
        } else alert("Error retrieving user")
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [userId])

    const updateUserData = (userId: string) => {
        if (userId) {
            findReviewsByUserId(userId)
                .then(setReviews)
                .catch(err => alert("Error finding reviews " + err.message))
            findSavedGamesByUserId(userId)
                .then(setSavedGames)
                .catch(err => alert("Error finding savedGames " + err.message))
        } else alert("Invalid userid")
    }

    return (
        <div>
            {thisUser ? (
                    <div className={"ProfilePanel"}>
                        <h2>Hello, {thisUser.username}</h2>
                        <div className={"Tabs"}>
                            <Tab className={"Tab"} label={"Library"} onClick={() => setTabToggle(true)}/>
                            <Tab className={"Tab"} label={"Reviews"} onClick={() => setTabToggle(false)}/>
                        </div>

                        {tabToggle ? <SavedGamesTab savedGames={savedGames}/> : <ReviewsTab reviews={reviews}/>}
                    </div>
                ) :
                (<LoginRedirect/>)
            }
        </div>
    );
}

function SavedGamesTab({savedGames}:{savedGames: SavedGame[] | undefined}) {
    return savedGames && savedGames.length > 0 ?
        <SavedGamesList games={savedGames}/> :
        <h3>Browse for games to add them to your library</h3>
}

function ReviewsTab({reviews}:{reviews: Review[] | undefined}) {
    return reviews && reviews.length > 0?
        <ReviewsListUserPage reviews={reviews}/> :
        <h3>Browse for games to review them</h3>
}

function LoginRedirect() {
    return (
        <div>
            <p>User not found</p>
            <NavLink to={"/"}>Return to home</NavLink>
        </div>
    )
}