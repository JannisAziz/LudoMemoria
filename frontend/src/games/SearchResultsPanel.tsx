import React from "react";
import GamesGrid from "./GamesGrid";
import {Game} from "./Game";
import CategoriesPopover from "./CategoriesPopover";

export default function SearchResultsPanel({games}: {games: Game[]}) {
    return (
        <div>
            <CategoriesPopover />
            <GamesGrid games={games}/>
        </div>
    );
}
