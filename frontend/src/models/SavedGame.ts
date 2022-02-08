import {Game} from "./Game";

export type SavedGame = {

    id: string

    game: Game

    timePlayed?: number
    achievements?: string[]
    notes?: string[]
}