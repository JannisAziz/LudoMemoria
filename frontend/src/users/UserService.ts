import {User} from "./User";

import axios from "axios"
import {Game} from "../games/Game";

const USER_STORAGE_KEY = "USER_STORE"

export const setLoggedInUser = (user: User) : void => {localStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user))}
export const getLoggedInUser = () : User => JSON.parse(localStorage.getItem(USER_STORAGE_KEY) || "{}")
export const clearLoggedInUser = () : void => localStorage.removeItem(USER_STORAGE_KEY)

export function findUserById(userId: string) {
    return axios
        .get(`/api/users/${userId}`)
        .then(response => response.data)
        .catch(console.error)
}

export function deleteUser(user: User) {
    console.log("DeleteUser: " + user)

}

export function saveGameToLibrary(game: Game) {

    const savedGame = {
        id: game.id,
        game: game
    }

    const user = getLoggedInUser()
    user.savedGames?.push(savedGame)
    setLoggedInUser(user)

    updateUser(user)
        .then(res => alert(res))
        .catch(console.error)
}

export function updateUser(user: User) {
    return axios
        .patch(`/api/users/update`, user)
        .then(response => response.data)
        .catch(console.error)
}

export function updatePassword(passwordOld: string, passwordNew: string) {
    console.log("UpdatePassword: " + passwordOld + "-" + passwordNew)
}

export function getReviews(gameId: string) {
    return axios
        .get(`/api/users/reviews/${gameId}`)
        .then(response => response.data)
        .catch(console.error)
}