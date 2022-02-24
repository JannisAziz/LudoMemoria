import axios from "axios";
import {Game} from "./Game";

export function browseAllByPage(resultsPerPage: number, currentPage: number) {
    return axios
        .get(`/api/search/browse&rpp=${resultsPerPage}&pg=${currentPage}`)
        .then(response => response.data as Game[])
}

export function searchByQuery(query: string) {
    return axios
        .get(`/api/search/query=${query}`)
        .then(response => response.data as Game[])
}

export function searchByName(name: string, resultsPerPage: number, currentPage: number) {
    return axios
        .get(`/api/search/name=${name}&rpp=${resultsPerPage}&pg=${currentPage}`)
        .then(response => response.data as Game[])
}

export function getGenres() {
    return axios
        .get(`/api/search/genres`)
        .then(response => response.data as string[])
}

export function searchByGenre(genre: string, resultsPerPage: number, currentPage: number) {
    return axios
        .get(`/api/search/genre=${genre}&rpp=${resultsPerPage}&pg=${currentPage}`)
        .then(response => response.data)
}

export function searchByRelease(release: number, resultsPerPage: number, currentPage: number) {
    return axios
        .get(`/api/search/release=${release}&rpp=${resultsPerPage}&pg=${currentPage}`)
        .then(response => response.data)
}