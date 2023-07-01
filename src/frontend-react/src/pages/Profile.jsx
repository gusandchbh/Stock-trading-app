import React, { useState } from "react";
import { Card, Form, Button, Alert} from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";
import { API } from "../api";
import 'simplebar-react/dist/simplebar.min.css';

export const Profile = () => {
    const { userToken, logout } = useAuth();
    const [email, setEmail] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");


    const updateEmail = async (e) => {
        e.preventDefault();
        try {
            const response = await API.put(`/api/v1/users/update/email/${userToken.userId}`, {
                email: email
            });
            setMessage(response.data);
        } catch (error) {
            setError(error.response.data);
        }
    }

    const updatePassword = async (e) => {
        e.preventDefault();
        try {
            const response = await API.put(`/api/v1/users/update/password/${userToken.userId}`, {
                password: newPassword
            });

            setMessage(response.data);
        } catch (error) {
            setError(error.response.data);
        }
    }

    const deleteUser = async () => {
        if(window.confirm("Are you sure you want to delete your account? This action is irreversible.")) {
            try {
                const response = await API.delete(`/api/v1/users/delete/${userToken.userId}`);
                setMessage(response.data);
                logout();
            } catch (error) {
                setError(error.response.data);
            }
        }
    }

    return (
        <div className="vh-100 bg-dark d-flex text-light">
            <Card className="text-center bg-dark border-0 mx-auto" style={{ width: "90vw", height: "80vh" }}>
                <Card.Body className="d-flex flex-column align-items-center text-light">
                    <h1>Welcome {userToken ? userToken.sub : "no user"}!</h1>
                    {message && <Alert variant="success">{message}</Alert>}
                    {error && <Alert variant="danger">{error}</Alert>}
                    <Form onSubmit={updateEmail} style={{ maxWidth: "500px", margin: "0 auto" }}>
                        <Form.Group>
                            <Form.Label>Email address</Form.Label>
                            <Form.Control
                                type="email"
                                required
                                value={email}
                                onChange={e => setEmail(e.target.value)}
                            />
                        </Form.Group>
                        <Button style={{ marginTop: "1rem" }} variant="primary" type="submit">
                            Update Email
                        </Button>
                    </Form>
                    <Form onSubmit={updatePassword} className="mt-4" style={{ maxWidth: "500px", margin: "0 auto" }}>
                        <Form.Group>
                            <Form.Label>New Password</Form.Label>
                            <Form.Control
                                type="password"
                                required
                                value={newPassword}
                                onChange={e => setNewPassword(e.target.value)}
                            />
                        </Form.Group>
                        <Button style={{ marginTop: "1rem" }} variant="primary" type="submit">
                            Change Password
                        </Button>
                    </Form>
                    <Button variant="danger" onClick={deleteUser} className="mt-4">
                        Delete Account
                    </Button>
                </Card.Body>
            </Card>
        </div>
    );
};

export default Profile;
