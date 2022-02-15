import {Game} from "./Game";
import GameCard from "./GameCard";
import "../styles/Styles.scss";
import React from "react";

export default function GamesList({games}: {games: Game[] | undefined}) {
    return (
        <article className={"GamesList"}>
            {games && games.length > 0 ?
                games.map(game => (<GameCard game={game}/>)) :
                ("No games yet")
            }
        </article>
    )
}