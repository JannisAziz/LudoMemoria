import React, {FormEventHandler, MouseEventHandler, useState} from "react";
import {Button, TextField} from "@mui/material";
import isValidEmail from "./EmailValidator";
import {resendConfirmationEmail, signUp} from "./LoginService";

export default function SignUpForm() {

    const [username, setUsername] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [passwordRepeat, setPasswordRepeat] = useState("")

    const onSignUpSubmit: FormEventHandler<HTMLFormElement>  = (e) => {
        e.preventDefault()

        if (username && email && password && passwordRepeat) {
            if (isValidEmail(email)) {
                if (password === passwordRepeat)
                    signUp(username, email.toLowerCase(), password).then(alert)
                else alert("Passwords dont match!")
            } else alert("Email invalid")
        } else alert("Empty fields not allowed")
    }

    const onResendConfirmationEmail: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        const email = prompt("Enter your email")

        if (email) {
            if (isValidEmail(email)) {
                resendConfirmationEmail(email.toLowerCase())
                alert("Email sent")
            } else alert("Email invalid")
        }
    }

    return (
        <form className={"LoginForm"} onSubmit={onSignUpSubmit}>
            <TextField onChange={e => setUsername(e.currentTarget.value)} label={"Username"} variant={"outlined"}/>
            <TextField onChange={e => setEmail(e.currentTarget.value)} label={"Email"} variant={"outlined"}/>
            <TextField onChange={e => setPassword(e.currentTarget.value)} type={"password"} label={"Password"} variant={"outlined"}/>
            <TextField onChange={e => setPasswordRepeat(e.currentTarget.value)} type={"password"} label={"Repeat Password"} variant={"outlined"}/>
            <Button type={"submit"} variant={"contained"}>Sign Up</Button>
            <Button onClick={onResendConfirmationEmail}>Resend confirmation email</Button>
        </form>
    )
}