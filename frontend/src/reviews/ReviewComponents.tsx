import {Review} from "./Review";
import React, {useState} from "react";
import {Button, List, ListItem, ListItemText, TextField} from "@mui/material";
import {ArrowDownward, ArrowUpward, Cancel, Delete, ModeEdit} from "@mui/icons-material";
import {buttonStyle} from "../styles/StyleOverwrites";
import {deleteReview, updateReview} from "./ReviewService";
import {getLoggedInUser} from "../users/UserService";
import {useNavigate} from "react-router-dom";
import ReviewAddPopover from "./ReviewAddPopover";
import {Game} from "../games/Game";
import {getLoggedInToken} from "../login/LoginService";

export function ReviewsListUserPage({reviews}: {reviews: Review[]}) {
    const [thisReviews, setThisReviews] = useState<Review[]>(reviews)

    const removeReview = (review: Review) => {
        const newReviews = thisReviews.filter(r => r.id !== review.id)
        setThisReviews([...newReviews])
    }

    return (
        <List className={"ReviewsList"}>
            {thisReviews.map((review, idx) =>
                 <ReviewListItem key={idx} review={review} onDeleted={removeReview} isGamePage={false}/>
            )}
        </List>
    )
}

export function ReviewsListGamePage({game, reviews}: {game: Game, reviews: Review[]}) {
    const [thisReviews, setThisReviews] = useState<Review[]>(reviews)
    const nav = useNavigate()

    const removeReview = (review: Review) => {
        const newReviews = [...thisReviews.filter(r => r.id !== review.id)]
        setThisReviews([...newReviews])
        nav(`/games/${game.id}`)
    }

    const addReview = (review: Review) => {
        const newReviews = [...reviews, review]
        setThisReviews([...newReviews])
        nav(`/games/${game.id}`)
    }


    return (
        <List className={"ReviewsList"}>
            <ReviewAddPopover game={game} onReviewAdded={addReview}/>
            {thisReviews.map((review, idx) =>
                <ReviewListItem key={idx} review={review} onDeleted={removeReview} isGamePage={true}/>
            )}
        </List>
    )
}

export function ReviewListItem({review, onDeleted, isGamePage}: {review: Review, onDeleted: (review: Review) => void, isGamePage: boolean}) {

    const [thisReview, setThisReview] = useState(review)
    const [votesUp, setVotesUp] = useState(thisReview.votesUp || 0)
    const [votesDown, setVotesDown] = useState(thisReview.votesDown || 0)
    const [description, setDescription] = useState(thisReview.description || "")

    const [editMode, setEditMode] = useState(false)

    const editAllowed = thisReview.userId === getLoggedInUser().id
    const voteAllowed = getLoggedInToken().length !== 0 && getLoggedInUser().id !== review.userId

    const onVoteUp = () => {
        setVotesUp(votesUp + 1)
        thisReview.votesUp = votesUp
        handleUpdate()
    }

    const onVoteDown = () => {
        setVotesDown(votesDown + 1)
        thisReview.votesDown = votesDown
        handleUpdate()
    }

    const onUpdateDesc = (newDesc: string) => {
        setDescription(newDesc)
        thisReview.description = description
        handleUpdate()
        setEditMode(false)
    }

    function handleUpdate() {
        updateReview(thisReview).then(setThisReview).catch(console.error)
    }

    const onDelete = () => {
        deleteReview(thisReview).then((deletedReview) => onDeleted(deletedReview)).catch(console.error)
        setEditMode(false)
    }

    const onToggleEditMode = () => {
        setEditMode(!editMode)
        setDescription(thisReview.description || "")
    }

    const nav = useNavigate()

    const gameNameButton =
        (<Button variant={"text"} onClick={() => nav(`/games/${thisReview.gameId}`)}>
            {thisReview.gameName}
        </Button>)

    const usernameButton =
        (<Button variant={"text"} onClick={() => nav(`/profile/${thisReview.userId}`)}>
            {thisReview.username}
        </Button>)

    return (
        <ListItem className={"ReviewListItem"}>
            <ListItemText
                primary={ isGamePage ? usernameButton : gameNameButton }
                secondary={
                    <TextField
                        className={"ReviewCardDesc"}
                        value={description}
                        onChange={e => setDescription(e.currentTarget.value)}
                        InputProps={{readOnly: !editMode}}
                        fullWidth={true}
                        multiline={true}
                    />
                }
            />
            <ReviewListItemButtons
                editMode={editMode}
                editAllowed={editAllowed}
                voteAllowed={voteAllowed}
                votesUp={votesUp}
                votesDown={votesDown}
                onVoteUp={onVoteUp}
                onVoteDown={onVoteDown}
                onUpdateDesc={onUpdateDesc}
                onDelete={onDelete}
                onToggleEditMode={onToggleEditMode}
            />
        </ListItem>
    )
}

function ReviewListItemButtons(
    {editMode, editAllowed, voteAllowed, votesUp, votesDown, onVoteUp, onVoteDown, onUpdateDesc, onDelete, onToggleEditMode} :
        {editMode: boolean, editAllowed: boolean, voteAllowed: boolean, votesUp: number, votesDown: number, onVoteUp: Function, onVoteDown: Function, onUpdateDesc: Function, onDelete: Function, onToggleEditMode: Function}) {

    return (
        <div>
            <Votes voteAllowed={voteAllowed} up={votesUp} down={votesDown} onUp={onVoteUp} onDown={onVoteDown}/>
            {editAllowed ?
                <EditModeButtons isEditMode={editMode}
                                  onUpdateDesc={onUpdateDesc}
                                  onDelete={onDelete}
                                  onToggleEditMode={onToggleEditMode}/> :
                undefined
            }
        </div>
    )
}

function EditModeButtons({isEditMode, onUpdateDesc, onDelete, onToggleEditMode} : {isEditMode: boolean, onUpdateDesc: Function, onDelete: Function, onToggleEditMode: Function}) {
    return isEditMode ?
        <div className={"EditModeButtons"} style={{"display": "flex", "flexDirection": "column"}}>
            <Button className={"Cancel"} style={buttonStyle("gray")} onClick={() => onToggleEditMode()}><Cancel/>Cancel</Button>
            <Button className={"Delete"} style={buttonStyle("red")} onClick={() => onDelete()}><Delete/>Delete</Button>
            <Button className={"Update"} style={buttonStyle("green")} onClick={() => onUpdateDesc()}><ModeEdit/>Save</Button>
        </div> :
        <div className={"EditModeButtons"}>
            <Button className={"Open"} style={buttonStyle("orange")} onClick={() => onToggleEditMode()}><ModeEdit/></Button>
        </div>
}

function Votes({voteAllowed, up, down, onUp, onDown}: {voteAllowed: boolean, up: number, down: number, onUp: Function, onDown: Function}) {
    return (
        <div className={"Votes"}>
            <Button className={"Up"}
                    disabled={!voteAllowed} onClick={e => {
                        e.preventDefault()
                        onUp()
                    }}
                    style={voteAllowed ? buttonStyle("green") : {"color": "green"}}>
                {up}<ArrowUpward/>
            </Button>
            <Button className={"Down"}
                    disabled={!voteAllowed} onClick={e => {
                        e.preventDefault()
                        onDown()
                    }}
                    style={voteAllowed ? buttonStyle("red") : {"color": "red"}}>
                {down}<ArrowDownward/>
            </Button>
        </div>
    )
}