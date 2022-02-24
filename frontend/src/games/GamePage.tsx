import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {Game, getImgUrl} from "./Game";
import {findGameById} from "./GameService";
import {Button, Skeleton, Tooltip} from "@mui/material";
import {getLoggedInUser, retrieveCurrentUser} from "../users/UserService";
import {Review} from "../reviews/Review";
import {findReviewsByGameId} from "../reviews/ReviewService";
import ScreenshotCarousel from "./ScreenshotCarousel";
import { v4 as uuid } from 'uuid';
import {SavedGame} from "./SavedGame";
import {addSavedGame, findSavedGamesByUserId} from "./SavedGameService";
import {getLoggedInToken} from "../login/LoginService";
import {ReviewsListGamePage} from "../reviews/ReviewComponents";

export default function GamePage() {
    const {gameId} = useParams()

    const [thisGame, setThisGame] = useState<Game>()
    const [reviews, setReviews] = useState<Review[]>()

    const [isSaved, setIsSaved] = useState(false)

    useEffect(() => {
        if (gameId) {
            findGameById(gameId).then(setThisGame).catch(console.error)
            findReviewsByGameId(gameId).then(setReviews).catch(console.error)

            const user = getLoggedInUser()

            findSavedGamesByUserId(user.id)
                .then(games => setIsSaved(games.filter(game => game.gameId === gameId).length !== 0))
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[gameId])

    const onSaveToLibrary = () => {
        if (thisGame) {

            if (getLoggedInToken().length === 0) return alert("Not logged in")

            const user = getLoggedInUser()

            if (!user.id) return alert("No user found")

            if (isSaved) return alert("Game already saved to library")

            const savedGame: SavedGame = {
                id: uuid(),
                userId: user.id,
                gameId: thisGame.id,
                notes: []
            }

            addSavedGame(savedGame)
                .then(() => {
                    retrieveCurrentUser().catch(console.error)
                    setIsSaved(true)
                })
                .catch(e => alert("Error adding saved game"))
        }
    }

    const nav = useNavigate()

    const isLoggedIn = getLoggedInToken().length !== 0

    return thisGame ?
        <div className={"GamePage"}>
            <h2>{thisGame.name}</h2>
            <div className={"GamePageButtons"}>
                {isSaved ?
                    <Button variant={"contained"} onClick={() => nav("/profile")}>Go to library</Button> :
                    <Tooltip disableHoverListener={isLoggedIn} placement={"top"} title="Please log in first">
                        <span>
                            <Button variant={"contained"} disabled={!isLoggedIn} onClick={onSaveToLibrary}>Save to library</Button>
                        </span>
                    </Tooltip>
                }
            </div>
            <img src={getImgUrl(thisGame.coverId, "cover_big")} alt={"coverImg"} />
            <p className={"GameDescription"}>{thisGame.description}</p>

            {thisGame.screenshotIds.length > 0 ?
                <ScreenshotCarousel screenshotIds={thisGame.screenshotIds}/> :
                ""
            }

            {reviews ?
                <ReviewsListGamePage game={thisGame} reviews={reviews}/> :
                <h3>Log in to add a review</h3>
            }
        </div> :
        <div className={"GamePage"}>
            <Skeleton variant={"rectangular"}/>
        </div>
}