import React, { useState } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const { signin } = useAuth();
    const navigate = useNavigate();

    async function handleSubmit(e) {
        e.preventDefault();

        try {
            setError("");
            setLoading(true);
            await signin(username, password);
            navigate("/");
        } catch (error) {
            setError("Failed to sign in");
            navigate("/login");
        }
        setLoading(false);
    }
    return (
        <div
            style={{
                display: "flex",
                justifyContent: "center",
                flexDirection: "column",
                alignContent: "center",
                alignItems: "center",
                marginTop: "5rem",
                marginBottom: "5rem",
            }}
        >
            <Card
                className="text-center"
                style={{
                    width: "22rem",
                    backgroundColor: "white",
                    borderRadius: "10%",
                }}
            >
                <Card.Body>
                    <h2 className="text-center mb-4">Log In</h2>
                    {error && <Alert variant="danger">{error}</Alert>}
                    <Form onSubmit={handleSubmit}>
                        <Form.Group id="username">
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                className="text-center"
                                required
                                onChange={(e) => {
                                    setUsername(e.target.value);
                                }}
                            />
                        </Form.Group>
                        <Form.Group id="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                className="text-center"
                                type="password"
                                required
                                onChange={(event) => {
                                    setPassword(event.target.value);
                                }}
                            />
                        </Form.Group>
                        <Button disabled={loading} className="w-40 mt-4" type="submit">
                            Log In
                        </Button>
                    </Form>
                    <div className="w-100 text-center mt-2">
                        <Link to="/forgot-password">Forgot Password?</Link>
                    </div>
                </Card.Body>
            </Card>
            <div className="w-100 text-center mt-4">
                Need an account? <Link to="/register">Sign Up</Link>
            </div>
            <h1 style={{ color: "transparent" }}>Test</h1>
        </div>
    );
};

export default Login;