import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./components/Login";
import ForgotPassword from "./pages/ForgotPassword";
import StartPage from "./pages/StartPage";
import Profile from "./pages/Profile";
import AuthProvider from "./contexts/AuthContext";
import StockPage from "./pages/StockPage";
import Portfolio from "./pages/Portfolio";
import PurchasePage from "./pages/PurchasePage";
import SalePage from "./pages/SalePage";

function App() {
  return (
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Layout />}>
              <Route index element={<StartPage />} />
              <Route path="/login" element={<Login />} />
              <Route path="/forgot-password" element={<ForgotPassword />} />  // Add this line
              <Route path="/profile" element={<Profile />} />
              <Route path="/stocks" element={<StockPage />} />
              <Route path="/portfolio" element={<Portfolio />} />
              <Route path="/purchase/:stockId" element={<PurchasePage />} />
              <Route path="/sell/:stockId" element={<SalePage /> } />
              <Route path="*" element={<h1>404 Not Found</h1>} />
            </Route>
          </Routes>
        </BrowserRouter>
      </AuthProvider>
  );
}

export default App;
