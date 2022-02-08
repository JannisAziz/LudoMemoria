import {Game} from "./Game";
import {Review} from "./Review";
import {User} from "./User";
import {SavedGame} from "./SavedGame";

export function TestReviews(): Review[]{
    return [
        {
            id: "1",
            userId: "user1",
            gameId: 'game1',
        },
        {
            id: "2",
            userId: "user2",
            gameId: 'game2',
        },
        {
            id: "3",
            userId: "user3",
            gameId: 'game3',
        },
        {
            id: "4",
            userId: "user4",
            gameId: 'game4',
        },
        {
            id: "5",
            userId: "user5",
            gameId: 'game5',
        }
    ]
}

export function TestGames(): Game[] {
    return [
        {
            id: '1',
            name: 'game1'
        },
        {
            id: '2',
            name: 'game2'
        },
        {
            id: '3',
            name: 'game3'
        },
        {
            id: '4',
            name: 'game4'
        },
        {
            id: '5',
            name: 'game5'
        }
    ]
}

export function TestSavedGames(): SavedGame[] {
    return [
        {
            id: '1',
            game: {id: "1", name: "game1"}
        },
        {
            id: '2',
            game: {id: "2", name: "game2"}
        },
        {
            id: '3',
            game: {id: "3", name: "game3"}
        },
    ]
}

export function TestUser(): User {
    return {
        id: "1",
        email: "user@mail.com",
        displayName: "username",
        password: "userPassword"
    }
}