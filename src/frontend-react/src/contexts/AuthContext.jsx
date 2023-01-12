/*
import React, {createContext, useContext, useState} from "react";

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState();
    const [registerEmail, setRegisterEmail] = useState("");
    const [registerPassword, setRegisterPassword] = useState("");
    const [loginEmail, setLoginEmail] = useState("");
    const [loginPassword, setLoginPassword] = useState("");

    const signup = async () => {
        try {
            const user = await createUserWithEmailAndPassword(
                auth,
                registerEmail,
                registerPassword
            );
            console.log(user);
        } catch (error) {
            console.log(error.message);
        }
    };

    const login = async () => {
        try {
            const user = await signInWithEmailAndPassword(
                auth,
                loginEmail,
                loginPassword
            );
            setCurrentUser(currentUser);
            console.log(user);
        } catch (error) {
            console.log(error.message);
            alert(" Wrong password or email, please try again");
        }
    };

    const logout = async () => {
        try {
            await signOut(auth);
            setCurrentUser(undefined);
        } catch (error) {
            console.log(error.message);
        }
    };

    const resetPassword = () => {
        const auth = getAuth();
        sendPasswordResetEmail(auth, loginEmail)
            .then(() => {
                alert("Password reset email sent!"); // Password reset email sent!
                // ..
            })
            .catch((error) => {
                console.log(error.message);
                alert(error.message);
            });
    };

    const updateEmail = async () => {
        try {
            const updatedEmail = await currentUser.updateEmail(auth, loginEmail);
            console.log(updatedEmail);
        } catch (error) {
            console.log(error.message);
        }
    };

    const value = {
        currentUser,
        login,
        signup,
        logout,
        resetPassword,
        updateEmail,
        setLoginEmail,
        setLoginPassword,
        setRegisterEmail,
        setRegisterPassword,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
export default AuthProvider;import React, {createContext, useContext, useState} from "react";

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState();
    const [registerEmail, setRegisterEmail] = useState("");
    const [registerPassword, setRegisterPassword] = useState("");
    const [loginEmail, setLoginEmail] = useState("");
    const [loginPassword, setLoginPassword] = useState("");

    const signup = async () => {
        try {
            const user = await createUserWithEmailAndPassword(
                auth,
                registerEmail,
                registerPassword
            );
            console.log(user);
        } catch (error) {
            console.log(error.message);
        }
    };

    const login = async () => {
        try {
            const user = await signInWithEmailAndPassword(
                auth,
                loginEmail,
                loginPassword
            );
            setCurrentUser(currentUser);
            console.log(user);
        } catch (error) {
            console.log(error.message);
            alert(" Wrong password or email, please try again");
        }
    };

    const logout = async () => {
        try {
            await signOut(auth);
            setCurrentUser(undefined);
        } catch (error) {
            console.log(error.message);
        }
    };

    const resetPassword = () => {
        const auth = getAuth();
        sendPasswordResetEmail(auth, loginEmail)
            .then(() => {
                alert("Password reset email sent!"); // Password reset email sent!
                // ..
            })
            .catch((error) => {
                console.log(error.message);
                alert(error.message);
            });
    };

    const updateEmail = async () => {
        try {
            const updatedEmail = await currentUser.updateEmail(auth, loginEmail);
            console.log(updatedEmail);
        } catch (error) {
            console.log(error.message);
        }
    };

    const value = {
        currentUser,
        login,
        signup,
        logout,
        resetPassword,
        updateEmail,
        setLoginEmail,
        setLoginPassword,
        setRegisterEmail,
        setRegisterPassword,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
export default AuthProvider;*/
