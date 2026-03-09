import { useState } from "react";
import { Link } from "react-router-dom";

import { loginUser } from "../utils/authApi";
import { saveToken } from "../utils/jwt";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();


const handleSubmit = async (e) => {
  e.preventDefault();

  try {
    const res = await loginUser({ email, password });
    console.log("Response:", res);
    console.log("Response:", res.data);
    saveToken(res.data);
    
   localStorage.setItem("token", res.data.token);
localStorage.setItem("userName", res.data.name);
localStorage.setItem("userEmail", res.data.email);
    navigate("/dashboard");
    console.log("username: " + res.data.name);
    
  } catch (err) {
    alert("Invalid email or password");
     console.log("Response:", err.data);
  }
};


  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100 bg-light">
      <div className="card shadow-sm p-4" style={{ width: "400px" }}>
        <h3 className="text-center mb-1">Smart Tracker</h3>
        <p className="text-center text-muted mb-4">
          Login to your account
        </p>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Email Address</label>
            <input
              type="email"
              className="form-control"
              placeholder="john@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="mb-4">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="btn btn-primary w-100">
            Login
          </button>
        </form>

        <div className="text-center mt-3">
  <span className="text-muted">Don’t have an account? </span>
  <Link to="/register" className="text-primary fw-semibold">
    Register
  </Link>
</div>

      </div>
    </div>
  );
}

export default Login;
