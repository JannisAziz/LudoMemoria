import {SavedGame} from "../games/SavedGame";
import {Game} from "../games/Game";
import {Review} from "../games/Review";

export type User = {

    id: string
    email: string
    username: string

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