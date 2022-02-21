import React, {useEffect, useState} from "react";
import {NavLink} from "react-router-dom";
import {retrieveCurrentUser} from "./UserService";
import ReviewCard from "../reviews/ReviewCard";
import {findReviewsByUserId} from "../reviews/ReviewService";
import {Review} from "../reviews/Review";
import {Tab} from "@mui/material";
import SavedGameCard from "../games/SavedGameCard";
import {User} from "./User";

export default function UserPage() {

    const [thisUser, setThisUser] = useState<User>()
    const [reviews, setReviews] = useState<Review[]>()
    const [tabToggle, setTabToggle] = useState(true)
    useEffect(() => {
        retrieveCurrentUser()
            .then(user => {
                setThisUser(user)
                updateReviews()
            })
            .catch(err => alert("Error retrieving user " + err.message))
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const updateReviews = () => {
        if (thisUser)
            findReviewsByUserId(thisUser.id).then(setReviews).catch(err => alert("Error finding reviews " + err.message))
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

                        {tabToggle ?
                            <div className={"GamesGrid"}>
                                {thisUser.savedGames && thisUser.savedGames.length > 0 ?
                                    thisUser.savedGames.map(game => (<SavedGameCard savedGame={game}/>)) :
                                    "Browse for games to add the to your library"
                                }
                            </div> :
                            <ul className={"ReviewList"}>
                                {reviews && reviews.length > 0 ?
                                    reviews.map((review, index) => (
                                        <li key={index}>
                                            <ReviewCard review={review} onUpdateReview={updateReviews} onDeleteReview={updateReviews}/>
                                        </li>)) :
                                    "Add games to your library to review them"
                                }
                            </ul>
                        }
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