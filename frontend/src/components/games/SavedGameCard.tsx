import {SavedGame} from "../../models/SavedGame";
import {MouseEventHandler} from "react";

export default function SavedGameCard({savedGame}: {savedGame: SavedGame}) {

    const onCardPressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log(`Open game page for ${savedGame.game.name}`)
    }

    return (
        <button onClick={onCardPressed}>
            {savedGame.id} - {savedGame.game.name} - TimePlayed: {savedGame.timePlayed || 0}
        </button>
    )
}