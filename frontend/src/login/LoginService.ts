import axios, {AxiosResponse} from "axios";
import {User} from "../users/User";
import {clearLoggedInUser, setLoggedInUser} from "../users/UserService";

export function signIn(username?: string, email?: string, password?: string) {
    return axios.post(
        "/api/auth/signIn/",
        {username, email, password})
        .then((res: AxiosResponse<User>) => setLoggedInUser(res.data))
        .then(() => "Signed in")
        .catch(console.error)
}

export function signUp(username?: string, email?: string, password?: string) {
    return axios.post(
        "/api/auth/signUp/",
        {username, email, password})
        .then((res:AxiosResponse) => res.data)
        .then(() => "Signed up")
        .catch(console.error)
}

export function resendConfirmationEmail(email?: string) {
    axios.post(
        `/api/auth/resendConfirmation=${email}/`)
        .then((res:AxiosResponse) => res.data)
        .catch(console.error)
}

export function signOut() {
    console.log("SignOut")
    clearLoggedInUser()
}
