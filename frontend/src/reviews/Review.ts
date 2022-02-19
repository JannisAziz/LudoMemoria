export type Review = {
    id: string
    userId: string
    username: string
    gameId: string
    gamename: string

    description?: string
    dateAdded?: string

    votesUp?: number
    votesDown?: number
}