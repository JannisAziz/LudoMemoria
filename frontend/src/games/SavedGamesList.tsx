import {SavedGame} from "./SavedGame";
import SavedGameCard from "./SavedGameCard";
import "../styles/Styles.scss";
import {useState} from "react";

export default function SavedGamesList({games}: {games: SavedGame[]}) {

    const [thisSavedGames, setThisSavedGames] = useState(games)

    const removeSavedGame = (removedGame: SavedGame) => {
        const newGames = thisSavedGames.filter(g => g.id !== removedGame.id)
        setThisSavedGames([...newGames])
    }

    return (
        <ul className={"GamesGrid"}>
            {games.map(game => (<li key={game.id}><SavedGameCard savedGame={game} onDeleted={removeSavedGame}/></li>))}
        </ul>
    )
}