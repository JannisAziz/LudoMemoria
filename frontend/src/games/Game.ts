import {Review} from "./Review";

export type Game = {
    id: string
    name: string
    description: string

    imageUrl: string

    votesUp?: number
    votesDown?: number

    reviews?: Review[]
}