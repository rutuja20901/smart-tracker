import { NavLink } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

function Sidebar() {

  const navigate = useNavigate();

  const [user, setUser] = useState({ name: "", email: "" });

  // read the stored name/email when the component mounts
  useEffect(() => {
    const name = localStorage.getItem("userName");
    const email = localStorage.getItem("userEmail");
    if (name || email) {
      setUser({ name: name || "", email: email || "" });
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
       // ✅ remove token and user details
    navigate("/login");                 // ✅ go to login
  };

  const navStyle = ({ isActive }) => ({
    display: "block",
    padding: "10px 15px",
    marginBottom: "10px",
    textDecoration: "none",
    borderRadius: "8px",
    color: "white",
    backgroundColor: isActive ? "#334155" : "transparent",
    fontWeight: isActive ? "bold" : "normal",
    transition: "background-color 0.3s",
  });

  return (
    <div style={styles.sidebar} className="app-sidebar">
      <h2 style={styles.logo}>Smart Tracker</h2>

      <nav>
        <NavLink to="/dashboard"
  style={navStyle}
  onMouseEnter={(e) => e.target.style.backgroundColor = "#475569"}
  onMouseLeave={(e) => e.target.style.backgroundColor = ""}>
  Dashboard
</NavLink>


        <NavLink to="/dashboard/expenses"
         style={navStyle} 
          onMouseEnter={(e) => e.target.style.backgroundColor = "#475569"}
           onMouseLeave={(e) => e.target.style.backgroundColor = ""}>Expenses</NavLink>
        <NavLink to="/dashboard/budget" style={navStyle}  onMouseEnter={(e) => e.target.style.backgroundColor = "#475569"}
  onMouseLeave={(e) => e.target.style.backgroundColor = ""}>Budget</NavLink>
        <NavLink to="/dashboard/reports" style={navStyle}  onMouseEnter={(e) => e.target.style.backgroundColor = "#475569"}
  onMouseLeave={(e) => e.target.style.backgroundColor = ""}>Reports</NavLink>

        <NavLink to="#"
  onClick={(e) => {
    e.preventDefault();   // prevent navigation
    handleLogout();
  }} style={navStyle}  onMouseEnter={(e) => e.target.style.backgroundColor = "#475569"}
  onMouseLeave={(e) => e.target.style.backgroundColor = ""}>Logout</NavLink>




      </nav>

<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
{/* User Info Section */}
<div style={styles.profileCard}>
  
  {/* Circle Avatar */}
  <div style={styles.avatar}>
    {user.name ? user.name.charAt(0).toUpperCase() : "U"}
  </div>

  {/* Name & Email */}
  <div style={styles.userDetails}>
    <p style={styles.userName}>{user.name || "User Name"}</p>
    <p style={styles.userEmail}>{user.email || "user@email.com"}</p>
  </div>

</div>

    </div>
  );
}

const styles = {
  sidebar: {
    width: "280px",
    height: "100vh",
    backgroundColor: "#1e293b",
    color: "white",
    padding: "20px",
    position: "fixed",
  },
  logo: {
    marginBottom: "30px",
  },
  profileCard: {
  marginTop: "30px",

  borderRadius: "12px",
  display: "flex",
  alignItems: "center",
  gap: "12px",
},

avatar: {
  width: "45px",
  height: "45px",
  borderRadius: "50%",
  backgroundColor: "#6366f1",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  fontSize: "18px",
  fontWeight: "bold",
  color: "white",
},

userDetails: {
  display: "flex",
  flexDirection: "column",
},

userName: {
  margin: 0,
  fontWeight: "bold",
  fontSize: "14px",
},

userEmail: {
  margin: 0,
  fontSize: "12px",
  color: "#cbd5e1",
},
};

export default Sidebar;