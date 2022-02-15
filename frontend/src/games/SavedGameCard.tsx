import {SavedGame} from "./SavedGame";
import {MouseEventHandler} from "react";

import "../styles/Styles.css"
import {Button} from "@mui/material";
import {Delete} from "@mui/icons-material";

export default function SavedGameCard({savedGame}: {savedGame: SavedGame}) {

    const onCardPressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log(`Open game page for ${savedGame.game.name}`)
    }

    return (
        <div>
            {savedGame.id} - {savedGame.game.name} - TimePlayed: {savedGame.timePlayed || 0}
            <Button><Delete /></Button>
            <Button onClick={onCardPressed}>
                <img className={"GameBackgroundImg"} src={savedGame.game.backgroundImg} alt={"bgImg"}/>
            </Button>
        </div>
    )
}