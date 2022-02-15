import {SavedGame} from "./SavedGame";
import SavedGameCard from "./SavedGameCard";
import "../styles/Styles.scss";

export default function SavedGamesList({games}: {games: SavedGame[] | undefined}) {
    return (
        <ul className={"SavedGamesList"}>
            {games && games.length > 0 ?
                games.map(game => (<li key={game.id}><SavedGameCard savedGame={game}/></li>)) :
                ("No saved games yet")}
        </ul>
    )
}