import axios from "axios"

export function getUserById(userId: string) {
    return axios
        .get(`/api/users/${userId}`)
        .then(response => response.data)
        .catch(console.error)
}