import React, {FormEventHandler, MouseEventHandler, useRef} from "react";
import {GithubLoginButton, GoogleLoginButton} from "react-social-login-buttons";

export default function Login() {

    const loginInputEmailUsername = useRef<HTMLInputElement>(null)
    const loginInputPassword = useRef<HTMLInputElement>(null)

    const registerInputEmailUsername = useRef<HTMLInputElement>(null)
    const registerInputPassword = useRef<HTMLInputElement>(null)
    const registerInputPasswordRepeat = useRef<HTMLInputElement>(null)

    const onLoginSubmit: FormEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault()
        console.log("onLoginSubmit")
    }

    const onRegisterSubmit: FormEventHandler<HTMLFormElement>  = (e) => {
        e.preventDefault()
        console.log("onRegisterSubmit")
    }

    const onForgotPassword: MouseEventHandler<HTMLButtonElement>  = (e) => {
        e.preventDefault()
        console.log("onForgotPassword")
    }

    const onSocialLogin = (network: string) => {
        console.log("onSocialLogin: " + network)
    }

    return (
        <div>
            <div>
                <form onSubmit={onLoginSubmit}>
                    Login
                    <input ref={loginInputEmailUsername} type={"text"} placeholder={"username/email"}/>
                    <input ref={loginInputPassword} type={"password"} placeholder={"password"}/>
                    <button type={"submit"}>Login</button>
                </form>
            </div>
            <div>
                <form onSubmit={onRegisterSubmit}>
                    Register
                    <input ref={registerInputEmailUsername} type={"text"} placeholder={"username/email"}/>
                    <input ref={registerInputPassword} type={"password"} placeholder={"password"}/>
                    <input ref={registerInputPasswordRepeat} type={"password"} placeholder={"repeat password"}/>
                    <button type={"submit"}>Register</button>
                </form>
            </div>
            <div>
                <button onClick={onForgotPassword}>Forgot Password</button>
            </div>
            <div>
                <GithubLoginButton align={"center"} onClick={() => onSocialLogin("github")}/>
                <GoogleLoginButton align={"center"} onClick={() => onSocialLogin("google")}/>
            </div>
        </div>
    );
}