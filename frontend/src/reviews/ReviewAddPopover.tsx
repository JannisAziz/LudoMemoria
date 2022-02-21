import React, {MouseEventHandler, useState} from "react";
import {Button, Popover, TextField} from "@mui/material";
import {Add, Cancel, RateReview} from "@mui/icons-material";
import {addReview} from "./ReviewService";
import { v4 as uuid } from 'uuid';
import {User} from "../users/User";
import {Game} from "../games/Game";
import {buttonStyle, textFieldStyle} from "../styles/StyleOverwrites";

export default function ReviewAddPopover({user, game, onAddReview}: {user: User, game: Game, onAddReview: Function}) {
    const [input, setInput] = useState("")

    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null)
    const open = Boolean(anchorEl)
    const id = open ? 'simple-popover' : undefined

    const onOpen: MouseEventHandler<HTMLButtonElement> = e => setAnchorEl(e.currentTarget)
    const onClose = () => setAnchorEl(null)

    const onAdd = () => {
        setAnchorEl(null)

        if (input) {
            const newReview = {
                id: uuid(),
                userId: user.id,
                username: user.username,
                gameId: game.id,
                gameName: game.name,
                description: input
            }

            addReview(newReview).catch(err => alert("Error adding review: " + err.message))
            onAddReview(newReview)
        }
    }

    return (
        <div>
            <Button aria-describedby={id} variant="outlined" onClick={onOpen}><RateReview/>Add Review</Button>

            <Popover id={id} open={open} onClose={onClose} anchorEl={anchorEl} anchorOrigin={{vertical: 'top', horizontal: 'left',}}>
                <div className={"ReviewForm"}>
                    <TextField className={"ReviewFormInput"}
                               label={"Add new review"}
                               onChange={e => setInput(e.currentTarget.value)}
                               style={textFieldStyle()}
                    />

                    <Button className={"ReviewFormCancel"} style={buttonStyle("grey")} onClick={onClose}><Cancel/></Button>
                    <Button className={"ReviewFormAdd"} style={buttonStyle("green")} onClick={onAdd}><Add/></Button>
                </div>
            </Popover>
        </div>
    )
}
