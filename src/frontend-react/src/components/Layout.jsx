import React from "react";
import { Outlet } from "react-router-dom";
import { Header } from "./Header";

function Layout() {
  return (
    <div style={{ backgroundColor: "#a9d1a9" }}>
      <Header />
      <Outlet />
    </div>
  );
}
export default Layout;
