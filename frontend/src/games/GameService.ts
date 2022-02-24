import axios from "axios";
import {Game} from "./Game";
import {getAxiosConfig} from "../login/LoginService";

export function findGameById(gameId: string) {
    return axios
        .get(`/api/games/${gameId}`)
        .then(response => response.data as Game)
}

export function findGamesByIds(gameIds: string[]) {
    return axios
        .post(`/api/games/ids`, gameIds)
        .then(response => response.data as Game[])
}

export function updateGame(game: Game) {
    return axios
        .patch("/api/games/update", game, getAxiosConfig())
        .then(response => response.data as Game)
}