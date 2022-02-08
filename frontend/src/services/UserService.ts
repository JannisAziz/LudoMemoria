import {User} from "../models/User";

import axios from "axios"

export function getUserById(userId: string) {
    return axios
        .get(`/api/users/${userId}`)
        .then(response => response.data)
        .catch(console.error)
}

export function DeleteUser(user: User) {
    console.log("DeleteUser: " + user)

}

export function UpdateUser(user: User) {
    console.log("UpdateUser: " + user)
}

export function UpdatePassword(passwordOld: string, passwordNew: string) {
    console.log("UpdatePassword: " + passwordOld + "-" + passwordNew)
}