import React, { useState } from "react";
import { Card, Button, Form, Alert, Spinner } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";
import 'bootstrap/dist/css/bootstrap.min.css';

const ForgotPassword = () => {
    const { resetPassword } = useAuth();
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();

        try {
            setMessage('');
            setError('');
            setLoading(true);
            await resetPassword(email);
            setMessage('Check your inbox for further instructions');
            setTimeout(() => setMessage(''), 5000);  // Clear message after 5 seconds
        } catch {
            setError('Failed to reset password');
            setTimeout(() => setError(''), 5000);  // Clear error after 5 seconds
        }

        setLoading(false);
    }

    return (
        <div className="vh-100 bg-dark d-flex text-light">
            <Card className="text-center bg-dark border-0 mx-auto" style={{width: "90vw", height: "80vh"}}>
                <Card.Body className="d-flex flex-column align-items-center text-light">
                    <h2 className="text-light mb-4">Reset Password</h2>
                    {error && <Alert variant="danger">{error}</Alert>}
                    {message && <Alert variant="success">{message}</Alert>}
                    <Form onSubmit={handleSubmit}>
                        <Form.Group id="email">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" required onChange={(e) => setEmail(e.target.value)}/>
                        </Form.Group>
                        <Button disabled={loading} className="w-100" type="submit">
                            {loading ? (
                                <Spinner animation="border" role="status">
                                    <span className="sr-only">Loading...</span>
                                </Spinner>
                            ) : 'Reset Password'}
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    );
}

export default ForgotPassword;
