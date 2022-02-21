import {User} from "./User";

import axios from "axios"
import {getAxiosConfig} from "../login/LoginService";

const USER_STORAGE_KEY = "USER_STORE"

export const setLoggedInUser = (user: User) : void => {localStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user))}
export const getLoggedInUser = () : User => JSON.parse(localStorage.getItem(USER_STORAGE_KEY) || "{}")
export const clearLoggedInUser = () : void => localStorage.removeItem(USER_STORAGE_KEY)

export function retrieveCurrentUser() {
    return axios
        .get("/api/users/currentUser", getAxiosConfig())
        .then(res => {
            setLoggedInUser(res.data)
            return res.data
        })
}

export function updateUser(user: User) {
    return axios
        .patch(`/api/users/update`, user, getAxiosConfig())
        .then(res => {
            setLoggedInUser(res.data)
            return res.data
        })
}