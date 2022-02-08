
export function SignIn(email?: string, password?: string) {
    if (email && password) {
        console.log("SignIn: " + email + "-" + password)
    }
}

export function OAuthSignIn() {
    console.log("OAuthSignIn")
}

export function OAuthSignUp() {
    console.log("OAuthSignUp")
}

export function SignUp(email?: string, password?: string) {
    if (email && password) {
        console.log("SignUp: " + email + "-" + password)
    }
}

export function SignOut() {
    console.log("SignOut")
}

export function ForgotPassword() {
    console.log("ForgotPassword")
}
