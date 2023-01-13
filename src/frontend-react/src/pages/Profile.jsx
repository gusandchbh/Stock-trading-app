import React, { useState, useEffect } from "react";
import { Card } from "react-bootstrap";
import API from "../api";

export const Profile = () => {
    const [user, setUser] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() =>  {
        fetchData()
    }, [])

    async function fetchData() {
        setLoading(true);
        setError(null);
        try {
            const response = await API.get('users/?username=' + localStorage.getItem('user'));
            console.log(localStorage.getItem('user'))
            setUser(response.data.username);
            console.log(response.data)
        } catch (error) {
            setError(error);
        }
        setLoading(false);
    }


    return (
        <div>
            <Card
                className="text-center"
                style={{
                    backgroundColor: "transparent",
                    border: "none",
                    height: "75vh",
                }}
            >
                <Card.Body className="profile-card">
                    {loading ? (
                        <p>Loading...</p>
                    ) : error ? (
                        <p>Error: {error.message}</p>
                    ) : (
                        <>
                            <h3>Welcome {user}</h3>
                        </>
                    )}
                </Card.Body>
            </Card>
            <div className="w-100 text-center mt-2"></div>
        </div>
    );
};

export default Profile;
