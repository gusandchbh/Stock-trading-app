import React, { useState, useEffect } from "react";
import { Modal, Button, FormControl, Form, Alert } from "react-bootstrap";
import { API } from "../api";
import { useAuth } from "../contexts/AuthContext";
import {BsCheckCircle} from "react-icons/bs";

const PurchaseModal = ({ stockId, show, handleClose }) => {
  const { userToken } = useAuth();
  const [quantity, setQuantity] = useState(1);
  const [stock, setStock] = useState(null);
  const [userStock, setUserStock] = useState(null);
  const [userPortfolio, setUserPortfolio] = useState(null);
  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertVariant, setAlertVariant] = useState("success");
  const [isSuccessful, setIsSuccessful] = useState(false);

  useEffect(() => {
    const fetchStock = async () => {
      try {
        const response = await API.get(`/api/v1/stocks/${stockId}`);
        setStock(response.data);
      } catch (e) {
        setAlertMessage("fetchStock");
        setAlertVariant("danger");
        setShowAlert(true);
      }
    };

    const fetchUserStock = async () => {
      if (!userToken) return;
      try {
        const response = await API.get(
          `/api/v1/users/${userToken.userId}/stocks/${stockId}`
        );
        setUserStock(response.data);
      } catch (e) {
        setAlertMessage(e.message);
        setAlertVariant("danger");
        setShowAlert(true);
      }
    };

    const fetchUserPortfolio = async () => {
      if (!userToken) return;
      try {
        const response = await API.get(`/api/v1/portfolio/${userToken.userId}`);
        setUserPortfolio(response.data);
      } catch (e) {
        setAlertMessage(e.message);
        setAlertVariant("danger");
        setShowAlert(true);
      }
    };
    if (stockId && userToken) {
      fetchStock();
      fetchUserStock();
      fetchUserPortfolio();
    }
  }, [stockId, userToken]);

  const handlePurchase = async () => {
    try {
      const response = await API.post(
          `/api/v1/users/${userToken.userId}/stocks/${stock.id}/purchase?quantity=${quantity}`
      );

      if (response.status === 200) {
        setIsSuccessful(true);

        setTimeout(() => {
          setIsSuccessful(false);
          handleClose();
        }, 2000);
      }
    } catch (e) {
      setAlertMessage(e.message);
      setAlertVariant("danger");
      setShowAlert(true);
    }
  };

  if (!stock || !userPortfolio) return null;

  const total = (stock.price * quantity).toFixed(2);
  const enoughBalance = userPortfolio.accountBalance >= total;

  if (isSuccessful) {
    return (
        <Modal show={show} onHide={handleClose} centered>
          <Modal.Body className="text-center">
            <BsCheckCircle size={60} color="green" /> {}
            <p>Your purchase was successful!</p>
          </Modal.Body>
        </Modal>
    );
  }

  return (
      <Modal show={show} onHide={handleClose} centered>
        {showAlert && (
            <Alert
                variant={alertVariant}
                onClose={() => setShowAlert(false)}
                dismissible
            >
              {alertMessage}
            </Alert>
        )}
        <Modal.Header closeButton>
          <Modal.Title>
            {stock.name} - {stock.ticker}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Price: ${stock.price.toFixed(2)}</p>
          <p>Balance: ${userPortfolio.accountBalance}</p>
          {userStock && <p>Current Holdings: {userStock.quantity}</p>}
          <Form.Group>
            <Form.Label>Quantity</Form.Label>
            <FormControl
                type="number"
                value={quantity}
                onChange={(e) => setQuantity(Number(e.target.value))}
                min="1"
            />
            <p style={{ color: enoughBalance ? "black" : "red" }}>
              Total: ${total}
            </p>
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button
              variant={enoughBalance ? "primary" : "secondary"}
              onClick={handlePurchase}
              disabled={!enoughBalance}
          >
            Buy
          </Button>
        </Modal.Footer>
      </Modal>
  );
};

export default PurchaseModal;
