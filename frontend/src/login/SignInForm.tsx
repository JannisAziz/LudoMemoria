import React, {FormEventHandler, MouseEventHandler, useState} from "react";
import {signIn} from "./LoginService";
import {Button, TextField} from "@mui/material";
import isValidEmail from "./EmailValidator";

export default function SignInForm() {

    const [usernameEmail, setUsernameEmail] = useState("")
    const [password, setPassword] = useState("")

    const onSignInSubmit: FormEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault()

        if (usernameEmail && password) {
            isValidEmail(usernameEmail) ?
                signIn(undefined, usernameEmail.toLowerCase(), password).then(alert) :
                signIn(usernameEmail, undefined, password).then(alert)
        }
        else {
            alert("Empty fields not allowed")
        }
    }

    const onForgotPassword: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        const email = prompt("Enter your email")

        if (email) {
            if (isValidEmail(email))
                //forgotPassword(email.toLowercase())
                alert("email sent to " + email)
            else alert("invalid email")
        }
    }

    return (
        <form className={"LoginForm"} onSubmit={onSignInSubmit}>
            <TextField onChange={e => setUsernameEmail(e.currentTarget.value)} label={"Username/Email"} variant={"outlined"}/>
            <TextField onChange={e => setPassword(e.currentTarget.value)} label={"Password"} type={"password"} variant={"outlined"} />
            <Button type={"submit"} variant={"contained"}>Sign In</Button>
            <Button onClick={onForgotPassword}>Forgot password</Button>
        </form>
    )
}