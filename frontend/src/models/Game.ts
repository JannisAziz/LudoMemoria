import {Review} from "./Review";

export type Game = {
    id: string
    name: string

    logoImg?: string
    backgroundImg?: string

    votesUp?: number
    votesDown?: number

    reviews?: Review[]

    // Extra game details

}