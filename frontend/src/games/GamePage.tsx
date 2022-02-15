import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Game} from "./Game";
import {findGameById} from "./GameService";
import ReviewList from "./ReviewList";

export default function GamePage() {
    const {gameId} = useParams()

    const [game, setGame] = useState<Game>()

    useEffect(() => {
        if (gameId) {
            findGameById(gameId)
                .then(setGame)
                .catch(console.error)
        }
        else return alert("No gameId")
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[])

    return (
        <div>
            {game ? (
                <div className={"GamePanel"}>
                    <div className={"GamePanelDetails"}>
                        <img src={"https:" + game.imageUrl.replace("thumb", "cover_big")} alt={"img"} />
                        <div>
                            <h2>{game.name}</h2>
                            <div>{game.description}</div>
                        </div>
                    </div>
                    <ReviewList reviews={game.reviews}/>
                </div>
            ) : "Game not found"}
        </div>
    )
}
