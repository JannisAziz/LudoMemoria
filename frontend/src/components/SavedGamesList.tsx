import {useEffect, useState} from "react";
import {getUserById} from "../services/UserService";
import {SavedGame} from "../models/SavedGame";
import SavedGameCard from "./SavedGameCard";
import {User} from "../models/User";

export default function SavedGamesList() {

    const [games, setGames] = useState<SavedGame[]>([])

    useEffect(() => {
        getUserById("TEST_ID")
            .then((user: User) => setGames(user.savedGames))
            .catch(console.error)
    }, [])

    return (
        <div>
            Games: 
            {games ? games.map(game => <SavedGameCard name={game.name}/>) : "No games found"}
        </div>
    )
}