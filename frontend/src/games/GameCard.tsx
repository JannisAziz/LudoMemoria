import {Game, getImgUrl} from "./Game";
import {MouseEventHandler} from "react";
import {useNavigate} from "react-router-dom";
import {Button} from "@mui/material";

export default function GameCard({game}: {game: Game}) {

    const nav = useNavigate();

    const onOpenGamePressed: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        console.log(`Open game page for ${game.name}`)
        nav("/games/" + game.id)
    }

    return (
        <div className={"GameCard"}>
            <img className={"GameImg"} src={getImgUrl(game.coverId, "cover_big")} alt={"coverImg"}/>
            <Button className={"GameCardOverlay"} onClick={onOpenGamePressed}>
                <h3>{game.name}</h3>
                {/*<div className={"GameCardOverlayIcons"}>*/}
                {/*    <Button variant={"contained"} onClick={onSaveGamePressed}>*/}
                {/*        <SaveTwoTone className={"SaveIcon"}/>*/}
                {/*    </Button>*/}
                {/*    <Button variant={"contained"} onClick={onOpenGamePressed}>*/}
                {/*        <OpenInFull className={"OpenIcon"}/>*/}
                {/*    </Button>*/}
                {/*</div>*/}
            </Button>
        </div>
    )
}
