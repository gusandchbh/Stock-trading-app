import React, { useState, useEffect } from "react";
import { Container, Card, Row, Col, Button, FormControl, Form } from "react-bootstrap";
import { API } from "../api";
import { useAuth } from "../contexts/AuthContext";
import { useNavigate, useParams } from "react-router-dom";

const SalePage = () => {
    const { userToken } = useAuth();
    const [quantity, setQuantity] = useState(1);
    const [stock, setStock] = useState(null);
    const [userStock, setUserStock] = useState(null);
    const navigate = useNavigate();
    const { stockId } = useParams();

    useEffect(() => {
        const fetchStock = async () => {
            try {
                const response = await API.get(`/api/v1/stocks/${stockId}`);
                setStock(response.data);
            } catch (e) {
                console.error(e);
            }
        };

        const fetchUserStock = async () => {
            if (!userToken) return;
            try {
                const response = await API.get(`/api/v1/users/${userToken.userId}/stocks/${stockId}`);
                setUserStock(response.data);
            } catch (e) {
                console.error(e);
            }
        };

        fetchStock();
        fetchUserStock();
    }, [stockId, userToken]);

    const handleSale = async () => {
        if (!userToken) {
            alert("Please login to sell stocks");
            return;
        }

        try {
            const response = await API.post(`/api/v1/users/${userToken.userId}/stocks/${stock.id}/sell?quantity=${quantity}`);

            if (response.status === 200) {
                alert(response.data);
                navigate("/"); // Redirect to home or any other page
            } else {
                alert(response.data.message);
            }
        } catch (e) {
            console.log(e);
        }
    };

    if (!stock || !userStock) return <p>Loading...</p>;

    // Check if user has enough stocks to sell
    const enoughStock = userStock.quantity >= quantity;

    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md="6">
                    <Card>
                        <Card.Body>
                            <Card.Title>{stock.name}</Card.Title>
                            <Card.Subtitle className="mb-2 text-muted">{stock.ticker}</Card.Subtitle>
                            <Card.Text>Price: ${stock.price.toFixed(2)}</Card.Text>
                            <Card.Text>Volume: {stock.volume}</Card.Text>
                            <Card.Text>Current Holdings: {userStock.quantity}</Card.Text>
                            <Form.Group>
                                <Form.Label id="inputGroup-sizing-default">Quantity</Form.Label>
                                <FormControl
                                    aria-label="Default"
                                    aria-describedby="inputGroup-sizing-default"
                                    type="number"
                                    value={quantity}
                                    onChange={(e) => setQuantity(Number(e.target.value))}
                                    min="1"
                                />
                            </Form.Group>
                            <Button variant="primary" onClick={handleSale} disabled={!enoughStock}>Sell</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default SalePage;
