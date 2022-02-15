import {Game} from "./Game";
import {MouseEventHandler} from "react";
import {saveGameToLibrary} from "../users/UserService";

export default function GameCard({game}: {game: Game}) {

    const onCardPressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log(`Open game page for ${game.name}`)
    }

    const onSaveGamePressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()
        saveGameToLibrary(game)
    }

    return (
        <div>
            <button onClick={onCardPressed}>
                {game.id} - {game.name}
            </button>
            <button onClick={onSaveGamePressed}>Save {game.name} to library</button>
        </div>
    )
}