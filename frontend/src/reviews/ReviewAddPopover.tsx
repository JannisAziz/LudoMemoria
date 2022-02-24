import React, {MouseEventHandler, useState} from "react";
import {Button, Popover, TextField, Tooltip} from "@mui/material";
import {Add, Cancel, RateReview} from "@mui/icons-material";
import {addReview} from "./ReviewService";
import { v4 as uuid } from 'uuid';
import {buttonStyle} from "../styles/StyleOverwrites";
import {getLoggedInUser} from "../users/UserService";
import {getLoggedInToken} from "../login/LoginService";
import {Review} from "./Review";
import {Game} from "../games/Game";

export default function ReviewAddPopover({game, onReviewAdded}: {game: Game, onReviewAdded: (review:Review) => void}) {
    const [input, setInput] = useState("")

    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null)
    const open = Boolean(anchorEl)
    const id = open ? 'simple-popover' : undefined

    const onOpen: MouseEventHandler<HTMLButtonElement> = (e) => {
        if (getLoggedInToken().length === 0)
            return alert("Not logged in")

        setAnchorEl(e.currentTarget)
    }
    const onClose = () => setAnchorEl(null)

    const onAdd = () => {
        setAnchorEl(null)

        if (input) {

            const user = getLoggedInUser()

            if (user.id) {
                const newReview = {
                    id: uuid(),
                    userId: user.id,
                    username: user.username,
                    gameId: game.id,
                    gameName: game.name,
                    description: input
                }

                addReview(newReview)
                    .then((addedReview) => onReviewAdded(addedReview))
                    .catch(err => alert("Error adding review: " + err.message))
            } else alert("Error adding review: User not found")
        }
    }

    const isLoggedIn = getLoggedInToken().length !== 0

    return (
        <div>
            <Tooltip disableHoverListener={isLoggedIn} placement={"bottom"} title="Please log in first">
                <span>
                    <Button aria-describedby={id} disabled={!isLoggedIn} variant="outlined" onClick={onOpen}><RateReview/>Add Review</Button>
                </span>
            </Tooltip>

            <Popover id={id} open={open} onClose={onClose} anchorEl={anchorEl} anchorOrigin={{vertical: 'top', horizontal: 'left',}}>
                <div className={"ReviewForm"}>
                    <TextField className={"ReviewFormInput"}
                               label={"Add new review"}
                               fullWidth={true}
                               multiline={true}
                               onChange={e => setInput(e.currentTarget.value)}
                    />

                    <Button className={"ReviewFormCancel"} style={buttonStyle("grey")} onClick={onClose}><Cancel/>Cancel</Button>
                    <Button className={"ReviewFormAdd"} style={buttonStyle("green")} onClick={onAdd}><Add/>Add</Button>
                </div>
            </Popover>
        </div>
    )
}
