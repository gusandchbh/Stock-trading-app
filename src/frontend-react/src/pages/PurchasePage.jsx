import React, { useState, useEffect } from "react";
import { Container, Card, Row, Col, Button, InputGroup, FormControl, Form } from "react-bootstrap";
import { API } from "../api";
import { useAuth } from "../contexts/AuthContext";
import { useNavigate, useParams } from "react-router-dom";

const PurchasePage = () => {
    const { userToken } = useAuth();
    const [quantity, setQuantity] = useState(1);
    const [stock, setStock] = useState(null);
    const [userStock, setUserStock] = useState(null);
    const [userPortfolio, setUserPortfolio] = useState(null);
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

        const fetchUserPortfolio = async () => {
            if (!userToken) return;
            try {
                const response = await API.get(`/api/v1/portfolio/${userToken.userId}`);
                setUserPortfolio(response.data);
            } catch (e) {
                console.error(e);
            }
        };

        fetchStock();
        fetchUserStock();
        fetchUserPortfolio();
    }, [stockId, userToken]);

    const handlePurchase = async () => {
        if (!userToken) {
            alert("Please login to purchase stocks");
            return;
        }

        try {
            const response = await API.post(`/api/v1/users/${userToken.userId}/stocks/${stock.id}/purchase?quantity=${quantity}`);

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

    if (!stock || !userPortfolio) return <p>Loading...</p>;

    // Calculate total
    const total = (stock.price * quantity).toFixed(2);

    // Check if user has enough balance
    const enoughBalance = userPortfolio.accountBalance >= total;

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
                            <Card.Text>Balance: ${userPortfolio.accountBalance}</Card.Text>
                            {userStock && <Card.Text>Current Holdings: {userStock.quantity}</Card.Text>}
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
                                <Card.Text style={{ color: enoughBalance ? 'black' : 'red' }}>Total: ${total}</Card.Text>
                            </Form.Group>
                            <Button variant="primary" onClick={handlePurchase} disabled={!enoughBalance}>Buy</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default PurchasePage;
