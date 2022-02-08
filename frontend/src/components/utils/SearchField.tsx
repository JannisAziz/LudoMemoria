import {FormEventHandler, useRef} from "react";

export default function SearchField({onSearch}: {onSearch: (input: string) => void}) {

    const userInputRef = useRef<HTMLInputElement>(null)

    const onSearchSubmit: FormEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault()

        if (userInputRef.current?.value) {
            onSearch(userInputRef.current.value)
            userInputRef.current.value = ""
        }
    }

    return (
        <form onSubmit={onSearchSubmit}>
            <input ref={userInputRef} type={"text"} placeholder={"search..."}/>
            <button type={"submit"}>Search</button>
        </form>
    )
}