import { useEffect, useState } from "react";
import { Card, Spinner } from "react-bootstrap";
import { useAuth } from "../contexts/AuthContext";
import { API } from "../api";
import PortfolioStockList from "../components/PortfolioStockList";
import SimpleBar from "simplebar-react";
import "simplebar-react/dist/simplebar.min.css";
import PortfolioTradeList from "../components/PortfolioTradeList";

export const Portfolio = () => {
  const { userToken } = useAuth();
  const [portfolio, setPortfolio] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [, setLoading] = useState(true);

  useEffect(() => {
    if (userToken) {
      API.get(`/api/v1/portfolio/${userToken.userId}`)
        .then((res) => {
          setPortfolio(res.data);
          setLoading(false);
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }, [userToken]);
  return (
    <div className="vh-100 bg-dark d-flex text-light">
      <Card
        className="text-center bg-dark border-0 mx-auto"
        style={{ width: "90vw", height: "80vh" }}
      >
        <Card.Body className="d-flex flex-column align-items-center text-light">
          {portfolio ? (
            <div>
              <div className="d-flex mt-4" style={{ width: "100%" }}>
                <div style={{ width: "253.98px", maxHeight: "349.49px" }}>
                  <h3 className="mb-3 text-light">Stocks</h3>
                  <SimpleBar style={{ maxHeight: "500px" }}>
                    <PortfolioStockList stocks={portfolio.stocks} />
                  </SimpleBar>
                </div>
                <div style={{ width: "507.96px", maxHeight: "349.49px" }}>
                  <h3 className="mb-3 text-light">Trades</h3>

                  <PortfolioTradeList
                    portfolioId={portfolio.id}
                    currentPage={currentPage}
                    setCurrentPage={setCurrentPage}
                  />
                </div>
              </div>
            </div>
          ) : (
            <div className="d-flex justify-content-center">
              <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
              </Spinner>
            </div>
          )}
        </Card.Body>
      </Card>
    </div>
  );
};

export default Portfolio;
