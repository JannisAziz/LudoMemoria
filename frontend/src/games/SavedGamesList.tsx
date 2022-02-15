import {SavedGame} from "./SavedGame";
import SavedGameCard from "./SavedGameCard";
import "../styles/Styles.css";

export default function SavedGamesList({games}: {games: SavedGame[]}) {
    return (
        <ul className={"SavedGamesList"}>
            {games.map(game => (<li key={game.id}><SavedGameCard savedGame={game}/></li>))}
        </ul>
    )
}