import React, { useState } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();

    try {
      setError("");
      setLoading(true);
      await login(username, password);
      navigate("/");
    } catch (error) {
      setError(error.toString());
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
        height: "90vh",
      }}
    >
      <Card
        className="text-center"
        style={{
          width: "35rem",
          height: "30rem",
          padding: "1rem",
          marginTop: "-2rem",
        }}
      >
        <Card.Body>
          <h2 className="text-center mb-4">Sign In</h2>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group id="username">
              <Form.Label>Email</Form.Label>
              <Form.Control
                className="text-center"
                required
                onChange={(e) => {
                  setUsername(e.target.value);
                }}
              />
            </Form.Group>
            <Form.Group id="password">
              <Form.Label className="mt-2">Password</Form.Label>
              <Form.Control
                className="text-center"
                type="password"
                required
                onChange={(event) => {
                  setPassword(event.target.value);
                }}
              />
            </Form.Group>
            <Button disabled={loading} className="w-100 mt-4" type="submit">
              Sign in
            </Button>
          </Form>
          <div style={{ display: "flex", marginTop: "1rem" }}>
            <div className="w-100 text-center mt-4">
              <Link to="/forgot-password">Forgot Password?</Link>
            </div>
            <div className="w-100 text-center mt-4">
              Need an account? <Link to="/register">Sign Up</Link>
            </div>
          </div>
        </Card.Body>
      </Card>
    </div>
  );
};

export default Login;
