import {SavedGame} from "./SavedGame";
import {Game} from "./Game";
import {Review} from "./Review";

export type User = {

    // Credentials

    id: string
    email: string
    displayName: string
    password: string

    oauthConnections?: string[]

    // User games & reviews

    savedGames?: SavedGame[]
    wishlistGames?: Game[]
    reviews?: Review[]

    // UserDetails

    description?: string
    birthdate?: string
    avatarImg?: string
    backgroundImg?: string

    socialLinks?: string[]
}