export type Review = {
    id: string
    userId: string
    gameId: string

    description?: string
    dateAdded?: string

    votesUp?: number
    votesDown?: number
}