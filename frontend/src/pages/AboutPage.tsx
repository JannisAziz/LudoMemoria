import React, {useEffect, useState} from "react";
import ReactMarkdown from "react-markdown";

export default function AboutPage() {

    const [stringMarkdown, setStringMarkdown] = useState("")

    useEffect(
        ()=>{
            fetch("/README.md").then(res => res.text()).then(setStringMarkdown)
        }, []
    )

    return (
        <div>
            About
            <ReactMarkdown children={stringMarkdown}/>
        </div>
    );
}