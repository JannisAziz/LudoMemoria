import {SavedGame} from "./SavedGame";
import {MouseEventHandler} from "react";

import "../styles/Styles.scss"
import {Button} from "@mui/material";
import {useNavigate} from "react-router-dom";

export default function SavedGameCard({savedGame}: {savedGame: SavedGame}) {

    const navigate = useNavigate()

    const onCardPressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log(`Open game page for ${savedGame.game.name}`)

        navigate(`/games/${savedGame.game.id}`)
    }

    return (
        <div>
            {savedGame.id} - {savedGame.game.name}
            {/*savedGame.id} - {savedGame.game.name} - TimePlayed: {savedGame.timePlayed || 0*/}
            {/*<Button><Delete/></Button>*/}
            <Button onClick={onCardPressed}>
                <img className={"GameImg"} src={"https:" + savedGame.game.imageUrl} alt={"bgImg"}/>
            </Button>
        </div>
    )
}