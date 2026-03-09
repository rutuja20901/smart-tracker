import { useLocation } from "react-router-dom";

function Navbar() {

  const location = useLocation();

  const path = location.pathname.split("/").filter(Boolean);

  const pageName = path.length > 1 
    ? `Dashboard / ${path[1].charAt(0).toUpperCase() + path[1].slice(1)}`
    : "Dashboard";

  return (
    <div style={navbarStyle}>
      <h3>{pageName}</h3>
    </div>
  );
}

const navbarStyle = {
  background: "#f1f5f9",
  padding: "15px",
  borderBottom: "1px solid #ddd"
};

export default Navbar;