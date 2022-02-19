import React, {useState} from "react";
import {Review} from "./Review";
import {ArrowDownward, ArrowUpward, Cancel, Delete, ModeEdit} from "@mui/icons-material";
import {Button, TextField} from "@mui/material";
import {getLoggedInUser} from "../users/UserService";
import {deleteReview, updateReview} from "./ReviewService";
import {buttonStyle} from "../styles/StyleOverwrites";

export default function ReviewCard({review, onUpdateReview, onDeleteReview}: {review: Review, onUpdateReview: Function, onDeleteReview: Function}) {

    const [thisReview, setThisReview] = useState(review)
    const [votesUp, setVotesUp] = useState(review.votesUp || 0)
    const [votesDown, setVotesDown] = useState(review.votesDown || 0)
    const [description, setDescription] = useState(review.description || "")

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
        setThisReview(updateReview(thisReview))//.catch(console.error)
        onUpdateReview(thisReview)
    }

    const onDelete = () => {
        deleteReview(review)//.catch(console.error)
        onDeleteReview(review)
        setEditMode(false)
    }

    const [editMode, setEditMode] = useState(false)
    const editAllowed = thisReview.userId === getLoggedInUser().id

    const onToggleEditMode = () => {
        setEditMode(!editMode)
        setDescription(thisReview.description || "")
    }

    return (
        <div className={"ReviewCard"}>
            <div className={"ReviewTitle"}>{review.username}</div>

            <TextField
                className={"ReviewCardDesc"}
                value={description}
                onChange={e => setDescription(e.currentTarget.value)}
                InputProps={{readOnly: !editMode}}
            />

            <ReviewCardButtons
                editMode={editMode}
                editAllowed={editAllowed}
                votesUp={votesUp}
                votesDown={votesDown}
                onVoteUp={onVoteUp}
                onVoteDown={onVoteDown}
                onUpdateDesc={onUpdateDesc}
                onDelete={onDelete}
                onToggleEditMode={onToggleEditMode}
            />
        </div>
    )
}

function ReviewCardButtons(
    {editMode, editAllowed, votesUp, votesDown, onVoteUp, onVoteDown, onUpdateDesc, onDelete, onToggleEditMode} :
    {editMode: boolean, editAllowed: boolean, votesUp: number, votesDown: number, onVoteUp: Function, onVoteDown: Function, onUpdateDesc: Function, onDelete: Function, onToggleEditMode: Function}) {

    return editAllowed ?
        (<EditModeButtons isEditMode={editMode} onUpdateDesc={onUpdateDesc} onDelete={onDelete} onToggleEditMode={onToggleEditMode}/>) :
        (<Votes up={votesUp} down={votesDown} onUp={onVoteUp} onDown={onVoteDown}/>)
}

function EditModeButtons({isEditMode, onUpdateDesc, onDelete, onToggleEditMode} : {isEditMode: boolean, onUpdateDesc: Function, onDelete: Function, onToggleEditMode: Function}) {

    return isEditMode ?
        <div className={"EditModeButtons"} style={{"display": "flex", "flexDirection": "column"}}>
            <Button className={"Cancel"} style={buttonStyle("gray")} onClick={() => onToggleEditMode()}><Cancel/></Button>
            <Button className={"Delete"} style={buttonStyle("red")} onClick={() => onDelete()}><Delete/></Button>
            <Button className={"Update"} style={buttonStyle("green")} onClick={() => onUpdateDesc()}><ModeEdit/></Button>
        </div> :
        <div className={"EditModeButtons"}>
            <Button className={"Open"} style={buttonStyle("orange")} onClick={() => onToggleEditMode()}><ModeEdit/></Button>
        </div>

}

function Votes({up, down, onUp, onDown}: {up: number, down: number, onUp: Function, onDown: Function}) {
    return (
        <div className={"Votes"}>
            <Button className={"Up"} onClick={() => onUp()} style={buttonStyle("green")}><ArrowUpward/>{up}</Button>
            <Button className={"Down"} onClick={() => onDown()} style={buttonStyle("red")}><ArrowDownward/>{down}</Button>
        </div>
    )
}
