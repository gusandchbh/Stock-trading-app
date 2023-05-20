import React from "react";
import { Outlet } from "react-router-dom";
import { Header } from "./Header";

function Layout() {
  return (
    <div style={{ backgroundColor: "#ADD8E6", height: "100%" }}>
      <Header />
      <Outlet />
    </div>
  );
}

export default Layout;
