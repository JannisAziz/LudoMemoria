import axios from "axios";

export function findGamesByName(name: string){
    return axios
        .get(`/api/games/search=${name}`)
        .then(response => response.data)
        .catch(console.error)
}