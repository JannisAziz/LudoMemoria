import {SavedGame} from "../models/SavedGame";

export default function SavedGameCard(game: SavedGame) {
    return (
        <div>
            {game.name}
        </div>
    )
}