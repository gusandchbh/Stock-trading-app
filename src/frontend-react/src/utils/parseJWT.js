/**
 * Decodes a JWT token
 * @param {string} token JWT Access Token
 * @returns Decoded value of the token
 */
const parseJWT = token => {
    if (!token)
        return
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(
        window
            .atob(base64)
            .split('')
            .map(c => {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
            })
            .join('')
    )

    return JSON.parse(jsonPayload)
}

export default parseJWT