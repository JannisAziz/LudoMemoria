import React, {useState} from "react";
import GamesList from "../games/GamesList";
import {Game} from "../games/Game";
import {findGamesByName} from "../games/GameService";
import SearchForm from "./SearchForm";

export default function CatalogPage() {

    const [games, setGames] = useState<Game[]>([])

    const onSearch = (input: string) => {
        findGamesByName(input)
            .then(res => setGames(res))
            .catch(console.error)
    }

    return (
        <div>
            <SearchForm onSearch={onSearch}/>
            {games?.length > 0 ? (<GamesList games={games}/>) : "No games here..."}
        </div>
    );
}