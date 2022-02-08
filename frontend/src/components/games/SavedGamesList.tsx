import {SavedGame} from "../../models/SavedGame";
import SavedGameCard from "./SavedGameCard";

export default function SavedGamesList({games}: {games: SavedGame[]}) {
    return (
        <ul>
            {games.map(game => (<li key={game.id}><SavedGameCard savedGame={game}/></li>))}
        </ul>
    )
}