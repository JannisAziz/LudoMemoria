import React, {FormEventHandler, MouseEventHandler, useState} from "react";
import {Button, TextField} from "@mui/material";
import isValidEmail from "./EmailValidator";
import {resendConfirmationEmail, signUp} from "./LoginService";
import {retrieveCurrentUser} from "../users/UserService";
import {useNavigate} from "react-router-dom";

export default function SignUpForm({onSubmit} : {onSubmit: Function}) {

    const nav = useNavigate()

    const [username, setUsername] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [passwordRepeat, setPasswordRepeat] = useState("")

    const onSignUpSubmit: FormEventHandler<HTMLFormElement>  = (e) => {
        e.preventDefault()

        if (!(username && email && password && passwordRepeat))
            return alert("Empty fields not allowed")
        if (!isValidEmail(email))
            return alert("Email invalid")
        if (password !== passwordRepeat)
            return alert("Passwords dont match!")

        signUp(username, email.toLowerCase(), password)
            .then(() => retrieveCurrentUser())
            .then(() => nav("/profile"))
            .catch(err => alert("Error retrieving user: " + err.message))
        onSubmit()
    }

    const onResendConfirmationEmail: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        const email = prompt("Enter your email")

        if (!email) return alert("Email invalid")

        if (isValidEmail(email)) {
            resendConfirmationEmail(email.toLowerCase())
            alert("Email sent")
            onSubmit()
        }
    }

    return (
        <form className={"SignUpForm"} onSubmit={onSignUpSubmit}>
            <TextField onChange={e => setUsername(e.currentTarget.value)} label={"Username"} variant={"outlined"}/>
            <TextField onChange={e => setEmail(e.currentTarget.value)} label={"Email"} variant={"outlined"}/>
            <TextField onChange={e => setPassword(e.currentTarget.value)} type={"password"} label={"Password"} variant={"outlined"}/>
            <TextField onChange={e => setPasswordRepeat(e.currentTarget.value)} type={"password"} label={"Repeat Password"} variant={"outlined"}/>
            <Button type={"submit"} variant={"contained"}>Sign Up</Button>
            <Button onClick={onResendConfirmationEmail}>Resend confirmation email</Button>
        </form>
    )
}