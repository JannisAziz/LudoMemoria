import {MouseEventHandler} from "react";

export default function FilterForm() {

    const onSearch: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()
        console.log("onSearch")
    }

    const onFilter: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()
        console.log("onFilter")
    }

    return (
        <form>
            <input placeholder={""} />
            <button onClick={onSearch}>Search</button>
            <button onClick={onFilter}>Filter</button>
        </form>
    )
}