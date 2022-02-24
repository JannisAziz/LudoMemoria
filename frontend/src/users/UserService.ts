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
            return res.data as User
        })
}

export function getUserById(userId: string) {
    return axios
        .get(`/api/users/id=${userId}`, getAxiosConfig())
        .then(res => res.data as User)
}

export function getUserByName(username: string) {
    return axios
        .get(`/api/users/name=${username}`, getAxiosConfig())
        .then(res => res.data as User)
}

export function updateUser(user: User) {
    return axios
        .patch("/api/users", user, getAxiosConfig())
        .then(res => {
            setLoggedInUser(res.data)
            return res.data as User
        })
}