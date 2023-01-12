import "./App.css";
import { Route, BrowserRouter, Routes } from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./pages/Login";
import StartPage from "./pages/StartPage";
import Register from "./pages/Register";
import Profile from "./pages/Profile";
import axios from 'axios';
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<StartPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
