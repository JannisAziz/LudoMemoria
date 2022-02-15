import {Game} from "./Game";
import {MouseEventHandler} from "react";
import {saveGameToLibrary} from "../users/UserService";
import {OpenInFull, SaveTwoTone} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";
import {Button} from "@mui/material";

export default function GameCard({game}: {game: Game}) {

    const nav = useNavigate();

    const onOpenGamePressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log(`Open game page for ${game.name}`)
        nav("/games/" + game.id)
    }

    const onSaveGamePressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()
        saveGameToLibrary(game)
    }

    return (
        <div className={"GameCard"}>
            <img className={"GameImg"} src={"https:" + game.imageUrl?.replace("thumb", "cover_big")} alt={"bgImg"}/>
            <div className={"GameCardOverlay"}>
                <h3>{game.name}</h3>
                <div className={"GameCardOverlayIcons"}>
                    <Button variant={"contained"} onClick={onSaveGamePressed}>
                        <SaveTwoTone className={"SaveIcon"}/>
                    </Button>
                    <Button variant={"contained"} onClick={onOpenGamePressed}>
                        <OpenInFull className={"OpenIcon"}/>
                    </Button>
                </div>
            </div>
        </div>
    )
}
