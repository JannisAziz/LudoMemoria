import {SavedGame} from "../games/SavedGame";

export type User = {
    id: string
    email: string
    username: string

    savedGames?: SavedGame[]
}