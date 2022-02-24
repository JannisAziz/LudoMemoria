import React, {useEffect, useRef, useState} from "react";
import {browseAllByPage, searchByGenre, searchByName} from "../games/SearchService";
import {Game} from "../games/Game";
import useOnScreen from "./OnSceenHook";
import SearchResultsPanel from "../games/SearchResultsPanel";
import {useParams} from "react-router-dom";
import {Skeleton} from "@mui/material";

export default function HomePage() {

    const {search, genre} = useParams()

    const [games, setGames] = useState<Game[]>([])
    const [resultsPerPage, setResultsPerPage] = useState(20)
    const [currentPage, setCurrentPage] = useState(0)

    const loaderPanel = useRef<HTMLDivElement>(null)
    const onScreen = useOnScreen(loaderPanel)
    useEffect(()=>{
        if (onScreen && games.length > 0) addSearch()

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [onScreen])

    useEffect(() => {
        setGames([])
        setCurrentPage(0)

        let response: Promise<Game[]>

        if (search) response = searchByName(search, resultsPerPage, currentPage)
        else if (genre) response = searchByGenre(genre, resultsPerPage, currentPage)
        else response = browseAllByPage(resultsPerPage, currentPage)

        response.then(games => setGames(games.sort((a, b) => a.name.localeCompare(b.name)))).catch(console.error)

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [search, genre])

    const addSearch = () => {
        const newPage = currentPage + 1

        setCurrentPage(newPage)

        let response: Promise<Game[]>

        if (search) response = searchByName(search, resultsPerPage, newPage)
        else if (genre) response = searchByGenre(genre, resultsPerPage, newPage)
        else response = browseAllByPage(resultsPerPage, newPage)

        response.then(res => {
            setGames([...games, ...res.sort((a, b) => a.name.localeCompare(b.name))])
        })
        .catch(console.error)
    }

    return (
        <div>

            {search ? <h3>Results for: {search}</h3> : undefined}
            {genre ? <h3>Results for: {genre}</h3> : undefined}

            {(!search && !genre) ?
                <div>
                    <h3>Welcome! Feel free to search for games from a library of more than 500k games</h3>
                    <h4>Most recent game releases</h4>
                </div> : undefined}

            <SearchResultsPanel games={games} />


            <Skeleton ref={loaderPanel} variant={"text"} />
        </div>
    );
}

