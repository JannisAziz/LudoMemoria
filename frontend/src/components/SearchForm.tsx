import {FormEventHandler, useState} from "react";
import {IconButton, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";

export default function SearchForm({onSearch}: {onSearch: (input: string) => void}) {

    const [searchText, setSearchText] = useState("")

    const onSearchSubmit: FormEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault()

        if (searchText.length > 0) onSearch(searchText)
    }

    return (
        <form className={"SearchForm"} onSubmit={onSearchSubmit}>
            <TextField
                className={"SearchFormTextField"}
                onChange={e => setSearchText(e.currentTarget.value)}
                placeholder={"Search"}
            />
            <IconButton type={"submit"}>
                <Search />
            </IconButton>
        </form>
    )
}
