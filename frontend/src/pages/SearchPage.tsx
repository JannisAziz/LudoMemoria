import SearchField from "../components/SearchField";
import GameCard from "../components/GameCard";
import {findGamesByName} from "../services/GameService";
import {useState} from "react";
import {Game} from "../models/Game";

export default function SearchPage() {

    const [searchResults, setSearchResults] = useState<Game[]>([])

    const onSearch = (userInput: string) => {
        findGamesByName(userInput)
            .then(setSearchResults)
            .catch(console.error)
    }

    return (
        <div>
            Home:
            <SearchField onSearch={onSearch}/>
            Search results:

            {searchResults?.length > 0 ? (<SearchResultsPanel results={searchResults}/>) : "No results..."}
        </div>
    )
}

function SearchResultsPanel({results}: {results: Game[]} ) {
    return (
        <div>
            {results.map(
                game => <GameCard game={game}/>
            )}
        </div>
    )
}
