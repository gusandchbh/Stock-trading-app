import React, { useState } from "react";
import { Alert, Button, Card, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Login = () => {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [showSignup, setShowSignup] = useState(false);

  const handleShowSignup = () => {
    setShowSignup(true);
  };

  const handleShowLogin = () => {
    setShowSignup(false);
  };
  const { login, signup } = useAuth();

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      setError("");
      setLoading(true);
      if (!showSignup) {
        const res = await login(username, password);
        console.log(res);
        setIsSignedIn(true);
      } else if (showSignup) {
        const res = await signup(username, password, email);
        console.log(res);
        setShowSignup(false);
      }
    } catch (error) {
      console.log(error.response.data);
      setError(error.response.data);
    }
    setLoading(false);
  }

  if (isSignedIn) return null;

  return (
    <div>
      <Card
        className="text-center"
        style={{
          width: "22rem",
          backgroundColor: "white",
          borderRadius: "10%",
        }}
      >
        {!showSignup ? (
          <>
            <Card.Body>
              <h2 className="text-center mb-4">Sign In</h2>
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
              <div className="w-100 text-center mt-4">
                <button
                  onClick={handleShowSignup}
                  style={{
                    backgroundColor: "transparent",
                    border: "none",
                    color: "var(--bs-link-color)",
                    textDdecoration: "underline",
                  }}
                >
                  Need an account? Sign up now
                </button>
              </div>
            </Card.Body>
          </>
        ) : (
          <Card.Body>
            <h2 className="text-center mb-4">Sign Up</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit}>
              <Form.Group id="username">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  className="text-center"
                  type="username"
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
                  onChange={(e) => {
                    setPassword(e.target.value);
                  }}
                />
              </Form.Group>
              <Form.Group id="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  className="text-center"
                  required
                  onChange={(e) => {
                    setEmail(e.target.value);
                  }}
                />
              </Form.Group>
              <Button disabled={loading} className="w-40 mt-4" type="submit">
                Sign Up
              </Button>
            </Form>
            <div className="w-100 text-center mt-4">
              <button
                onClick={handleShowLogin}
                style={{
                  backgroundColor: "transparent",
                  border: "none",
                  color: "var(--bs-link-color)",
                  textDecoration: "underline",
                }}
              >
                Already have an account? Sign in
              </button>
            </div>
          </Card.Body>
        )}
      </Card>
    </div>
  );
};

export default Login;
