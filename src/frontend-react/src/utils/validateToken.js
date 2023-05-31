function isTokenValid(token) {
    const decodedToken = parseJWT(token);
    if (!decodedToken) {
        return false;
    }

    // Check if the token is expired
    const currentTimestamp = Math.floor(Date.now() / 1000);
    return !(decodedToken.exp && decodedToken.exp < currentTimestamp);


}