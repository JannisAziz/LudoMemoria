import axios from "axios";
import {getAxiosConfig} from "../login/LoginService";
import {SavedGame} from "./SavedGame";

export function findSavedGamesByUserId(userId: string) {
    return axios
        .get(`/api/savedGames/userId=${userId}`)
        .then(response => response.data as SavedGame[])
}

export function addSavedGame(game: SavedGame) {
    return axios
        .put("/api/savedGames", game, getAxiosConfig())
        .then(response => response.data as SavedGame)
}
export function updateSavedGame(game: SavedGame) {
    return axios
        .patch("/api/savedGames", game, getAxiosConfig())
        .then(response => response.data as SavedGame)
}
export function deleteSavedGame(game: SavedGame) {
    return axios
        .delete(`/api/savedGames/id=${game.id}`, getAxiosConfig())
        .then(response => response.data as SavedGame)
}