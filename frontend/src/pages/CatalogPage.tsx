import React, {useState} from "react";
import GamesList from "../components/games/GamesList";
import SearchField from "../components/utils/SearchField";
import {Game} from "../models/Game";
import {TestGames} from "../models/TESTDATA";
import FilterForm from "../components/utils/FilterForm";

export default function CatalogPage() {

    const [games, setGames] = useState<Game[]>([])

    const onSearch = (input: string) => {
        console.log(`Search: ${input}`)

        setGames(TestGames());
    }

    return (
        <div>
            Catalog
            <SearchField onSearch={onSearch}/>
            <FilterForm/>
            {games.length > 0 ?(<GamesList games={games}/>) : "No games here..."}
        </div>
    );
}