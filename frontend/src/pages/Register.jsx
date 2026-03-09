import { useState } from "react";
import { Link } from "react-router-dom";
import { registerUser } from "../utils/authApi";
import { useNavigate } from "react-router-dom";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [roles, setRole] = useState("ROLE_USER");
  const [error, setError] = useState("");

  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    e.preventDefault();

    const registerRequest = {
      name,
      email,
      password,
      roles: roles,
    };

    try {
      const response = await registerUser(registerRequest);
      // console.log("Registration success:", response.data);
      alert("Registration successful! Please login.");
      navigate("/login");
    } catch (error) {
      setError(error.response?.data?.message || "This email is already registered");
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "420px" }}>
        <h3 className="text-center mb-2">Smart Tracker</h3>
        <p className="text-center text-muted mb-4">
          Create your account
        </p>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Full Name</label>
            <input
              type="text"
              className="form-control"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <div className="mb-4">
            <label className="form-label">Role</label>
            <select
              className="form-select"
              value={roles}
              onChange={(e) => setRole(e.target.value)}
            >
              <option value="ROLE_USER">USER</option>
              <option value="ROLE_ADMIN">ADMIN</option>
            </select>
          </div>

{error && <p className="error-message" style={{ color: "red", fontSize: "14px", marginBottom: "10px" }}>
            {error}
          </p>}
          <button type="submit" className="btn btn-primary w-100">
            Register
          </button>
        </form>

        <p className="text-center mt-3">
          Already have an account?{" "}
          <Link to="/login" className="text-decoration-none">
            Login
          </Link>
        </p>
      </div>
    </div>
  );
}

export default Register;
