import {Game} from "../models/Game";
import {MouseEventHandler} from "react";

export default function GameCard({game}: {game: Game}) {

    const onCardPressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log("Open game page")
    }

    return (
        <button onClick={onCardPressed}>
            {game.id} - {game.name}
        </button>
    )
}