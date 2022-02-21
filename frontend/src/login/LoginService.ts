import axios, {AxiosResponse} from "axios";
import {clearLoggedInUser} from "../users/UserService";

const TOKEN_KEY = "LOGGED_IN_TOKEN"

export const setLoggedInToken = (token: string) : void => localStorage.setItem(TOKEN_KEY, token)
export const getLoggedInToken = () : string => localStorage.getItem(TOKEN_KEY) || ""
export const clearLoggedInToken = () : void => localStorage.removeItem(TOKEN_KEY)

export const getAxiosConfig = () : {headers: {'Authorization': string}} =>
    ({headers: {'Authorization': 'Bearer ' + getLoggedInToken()}})

export function signIn(username: string, email: string, password: string) {
    return axios.post(
        "/api/auth/signIn/",
        {username, email, password})
        .then(res => setLoggedInToken(res.data))
}

export function signUp(username: string, email: string, password: string) {
    return axios.post(
        "/api/auth/signUp/",
        {username, email, password})
        .then(res => setLoggedInToken(res.data))
}

export function resendConfirmationEmail(email: string) {
    axios.post(
        `/api/auth/resendConfirmation=${email}/`)
        .then((res:AxiosResponse) => res.data)
}

export function signOut() {
    clearLoggedInUser()
    clearLoggedInToken()
}
