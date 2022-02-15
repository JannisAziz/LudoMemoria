import {FormEventHandler, useState} from "react";
import {Button, TextField} from "@mui/material";
import {Search} from "@mui/icons-material";

export default function SearchForm({onSearch}: {onSearch: (input: string) => void}) {

    const [searchText, setSearchText] = useState("")

    const onSearchSubmit: FormEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault()

        if (searchText.length > 0)
            onSearch(searchText)
    }

    return (
        <form className={"SearchForm"} onSubmit={onSearchSubmit}>
            <TextField onChange={e => setSearchText(e.currentTarget.value)} label={"Search"}/>
            <Button type={"submit"} variant={"outlined"}><Search/></Button>
        </form>
    )
}
