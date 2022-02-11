import React, {useState} from "react";
import GamesList from "../games/GamesList";
import SearchField from "./SearchField";
import {Game} from "../games/Game";
import {findGamesByName} from "../games/GameService";

export default function CatalogPage() {

    const [games, setGames] = useState<Game[]>([])

    const onSearch = (input: string) => {
        console.log(`Search: ${input}`)

        findGamesByName(input)
            .then(res => setGames(res))
            .catch(console.error)
    }

    return (
        <div>
            Catalog
            <SearchField onSearch={onSearch}/>
            {games?.length > 0 ? (<GamesList games={games}/>) : "No games here..."}
        </div>
    );
}