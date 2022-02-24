import React, {MouseEventHandler, useEffect, useState} from "react";
import {Button, ButtonGroup, Popover} from "@mui/material";
import {getGenres} from "./SearchService";
import {useNavigate} from "react-router-dom";

export default function CategoriesPopover() {
    const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null)
    const open = Boolean(anchorEl)
    const id = open ? 'simple-popover' : undefined

    const onOpen: MouseEventHandler<HTMLButtonElement> = e => setAnchorEl(e.currentTarget)
    const onClose = () => setAnchorEl(null)

    const [genres, setGenres] = useState([""])
    useEffect(() => {
        getGenres().then(res => setGenres(res))
            .catch(console.error)
    },[])

    const nav = useNavigate()

    const onGenreSearch = (genre: string) => {
        setAnchorEl(null)
        nav(`/genre=${genre}`)
    }

    return (
        <div className={"CategoriesPopover"}>
            <Button aria-describedby={id} variant="contained" onClick={onOpen} sx={{"fontSize": "smaller"}}><div>Browse by Categories</div></Button>
            <Popover className={"CategoriesPopoverPanel"}
                     id={id}
                     open={open} onClose={onClose}
                     anchorEl={anchorEl} anchorOrigin={{vertical: 'bottom', horizontal: 'left',}}>
                <ButtonGroup className={"CategoriesButtons"} variant="contained" aria-label="outlined primary button group">
                    {genres?.length > 0 ? genres.map(genre => (<Button key={genre} onClick={() => onGenreSearch(genre)}>{genre}</Button>)) : "No genres"}
                </ButtonGroup>
            </Popover>
        </div>
    )
}
