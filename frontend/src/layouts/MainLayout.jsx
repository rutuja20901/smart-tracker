import { Outlet } from "react-router-dom";
import Sidebar from "../components/Sidebar";

function MainLayout() {
  return (
    <div className="app-layout">
      <Sidebar />
      <div className="content">
        <Outlet />
      </div>
    </div>
  );
}

export default MainLayout;