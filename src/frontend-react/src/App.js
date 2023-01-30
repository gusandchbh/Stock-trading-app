import "./App.css";
import { Route, BrowserRouter, Routes } from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./pages/Login";
import StartPage from "./pages/StartPage";
import Register from "./pages/Register";
import Profile from "./pages/Profile";
import AuthProvider from "./contexts/AuthContext";
import StockPage from "./pages/StockPage";
function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<StartPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/stocks" element={<StockPage />} />
            <Route path="*" element={<h1>404 Not Found</h1>} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
