import React, { useState } from "react";
import {Card, Form, Button, Alert, Container, Row, Col} from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";
import { API } from "../api"

export const Profile = () => {
    const { userToken } = useAuth();
    const [email, setEmail] = useState("");
    const [oldPassword, setOldPassword] = useState("");
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
                oldPassword: oldPassword,
                newPassword: newPassword
            });

            setMessage(response.data);
        } catch (error) {
            setError(error.message);
        }
    }

    return (
        <div style={{ height: "100vh" }}>
            <Container>
                <Row>
                    <Col xs={12} md={6} className="mx-auto">
                        <Card
                            className="text-center"
                            style={{
                                backgroundColor: "transparent",
                                border: "none",
                                height: "75vh",
                            }}
                        >
                            <Card.Body className="profile-card" style={{maxWidth: "500px", margin: "0 auto"}}>
                                <h1>Welcome {userToken ? userToken.sub : "no user"}!</h1>
                                {message && <Alert variant="success">{message}</Alert>}
                                {error && <Alert variant="danger">{error}</Alert>}
                                <Form onSubmit={updateEmail} style={{maxWidth: "500px", margin: "0 auto"}}>
                                    <Form.Group>
                                        <Form.Label>Email address</Form.Label>
                                        <Form.Control
                                            type="email"
                                            required
                                            value={email}
                                            onChange={e => setEmail(e.target.value)}
                                        />
                                    </Form.Group>
                                    <Button variant="primary" type="submit">
                                        Update Email
                                    </Button>
                                </Form>
                                <Form onSubmit={updatePassword} className="mt-4" style={{maxWidth: "500px", margin: "0 auto"}}>
                                    <Form.Group>
                                        <Form.Label>Old Password</Form.Label>
                                        <Form.Control
                                            type="password"
                                            required
                                            value={oldPassword}
                                            onChange={e => setOldPassword(e.target.value)}
                                        />
                                    </Form.Group>
                                    <Form.Group>
                                        <Form.Label>New Password</Form.Label>
                                        <Form.Control
                                            type="password"
                                            required
                                            value={newPassword}
                                            onChange={e => setNewPassword(e.target.value)}
                                        />
                                    </Form.Group>
                                    <Button variant="primary" type="submit">
                                        Change Password
                                    </Button>
                                </Form>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
            <div className="w-100 text-center mt-2"></div>
        </div>
    );
};

export default Profile;
