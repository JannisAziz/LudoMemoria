export default function isValidEmail(email: string) {
    const EMAIL_PATTERN = /^[^@\s]+@[^@\s]+\.[^@\s]+$/

    return EMAIL_PATTERN.test(email)
}