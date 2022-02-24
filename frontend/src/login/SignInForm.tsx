import React, {FormEventHandler, MouseEventHandler, useState} from "react";
import {signIn} from "./LoginService";
import {Button, TextField} from "@mui/material";
import isValidEmail from "./EmailValidator";
import {retrieveCurrentUser} from "../users/UserService";
import {useNavigate} from "react-router-dom";

export default function SignInForm({onSubmit}: {onSubmit: Function}) {

    const nav = useNavigate()

    const [usernameEmail, setUsernameEmail] = useState("")
    const [password, setPassword] = useState("")

    const onSignInSubmit: FormEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault()

        if (usernameEmail.length === 0 && !password)
            return alert("Empty fields not allowed")

        const response = isValidEmail(usernameEmail) ?
            signIn("", usernameEmail.toLowerCase(), password) :
            signIn(usernameEmail, "", password)

        response.then(() => retrieveCurrentUser())
                .then(() => nav("/profile"))
                .catch(err => alert("Invalid Credentials"))

        onSubmit()
    }

    const onForgotPassword: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.preventDefault()

        const email = prompt("Enter your email")

        if (!email) return alert("invalid email")

        if (isValidEmail(email))
            alert("email sent to " + email)
            //forgotPassword(email.toLowercase())

        onSubmit()
    }

    return (
        <form className={"SignInForm"} onSubmit={onSignInSubmit}>
            <TextField onChange={e => setUsernameEmail(e.currentTarget.value)} label={"Username/Email"} variant={"outlined"}/>
            <TextField onChange={e => setPassword(e.currentTarget.value)} label={"Password"} type={"password"} variant={"outlined"} />
            <Button type={"submit"} variant={"contained"}>Sign In</Button>
            <Button onClick={onForgotPassword}>Forgot password</Button>
        </form>
    )
}