import {SavedGame} from "./SavedGame";
import {MouseEventHandler, useEffect, useState} from "react";

import "../styles/Styles.scss"
import {Button, Skeleton} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {OpenInFull, RemoveCircle} from "@mui/icons-material";
import {Game, getImgUrl} from "./Game";
import {findGameById} from "./GameService";
import {deleteSavedGame} from "./SavedGameService";
import {getLoggedInUser} from "../users/UserService";

export default function SavedGameCard({savedGame, onDeleted}: {savedGame: SavedGame, onDeleted: (savedGame: SavedGame) => void}) {

    const nav = useNavigate();

    const [thisGame, setThisGame] = useState<Game>()

    useEffect(() => {
        findGameById(savedGame.gameId)
            .then(setThisGame)
            .catch(console.error)

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const onOpenGamePressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        if (thisGame) nav(`/games/${thisGame.id}`)
    }

    const onDeleteGamePressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        if (savedGame) {
            deleteSavedGame(savedGame)
                .then((deletedGame) => onDeleted(deletedGame))
                .catch(console.error)
        }
    }

    const isOwnLibrary = (getLoggedInUser().id === savedGame.userId) || false

    return thisGame ? (
        <div className={"GameCard"}>
            <img className={"GameImg"} src={getImgUrl(thisGame.coverId, "cover_big")} alt={"coverImg"}/>
            <div className={"GameCardOverlay"}>
                <h3>{thisGame.name}</h3>
                <div className={"GameCardOverlayIcons"}>
                    <Button variant={"contained"} onClick={onOpenGamePressed}>
                        <OpenInFull className={"OpenIcon"}/>
                        Open
                    </Button>
                    {isOwnLibrary ?
                        <Button variant={"contained"} onClick={onDeleteGamePressed}>
                            <RemoveCircle className={"DeleteIcon"}/>
                            Remove
                        </Button> :
                        undefined
                    }
                </div>
            </div>
        </div> ) : (
        <div className={"GameCard"}>
            <Skeleton className={"GameImg"} variant={"rectangular"}/>
        </div> )
}