import {Game} from "./Game";
import GameCard from "./GameCard";
import "../styles/Styles.scss";
import React from "react";

export default function GamesGrid({games}: {games: Game[] | undefined}) {
    return (
        <div className={"GamesGrid"}>
            {games && games.length > 0 ?
                games.map(game => (<li key={game.id}><GameCard game={game}/></li>)) :
                ("If loading persists for too long try refreshing the page")
            }
        </div>
    )
}