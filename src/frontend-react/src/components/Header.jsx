import React from "react";
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "../contexts/AuthContext";
import {Button} from "react-bootstrap";

export const Header = () => {
    const {currentUser, logout} = useAuth()
    const navigate = useNavigate()

    const handleLogout = () => {
        logout()
        navigate("/login")
    }

    return (
        <div
            style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-evenly",
            }}
        >
            <Link to="/">
                <h1 style={{color: "#333"}}>Home</h1>
            </Link>
            {!currentUser && (
                <Link to="/login">
                    <h1 style={{color: "#333"}}>Login</h1>
                </Link>

            )}
            {!currentUser && (
                <Link to="/register">
                    <h1 style={{color: "#333"}}>Register</h1>
                </Link>)
            }
            {currentUser && (
                <Link to="/stocks">
                    <h1 style={{color: "#333"}}>Stocks</h1>
                </Link>

            )}
            {currentUser && (
                <>
                    <Link to="/profile">
                        <h1 style={{color: "#333"}}>Profile</h1>
                    </Link>
                    <Button style={{color: "#333"}} onClick={handleLogout}>Sign out</Button>
                </>
            )}

        </div>
    );
};
