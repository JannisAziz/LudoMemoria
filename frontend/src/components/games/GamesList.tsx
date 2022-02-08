import {Game} from "../../models/Game";
import GameCard from "./GameCard";

export default function GamesList({games}: {games: Game[]}) {
    return (
        <ul>
            {games.map(game => (<li key={game.id}><GameCard game={game}/></li>))}
        </ul>
    )
}