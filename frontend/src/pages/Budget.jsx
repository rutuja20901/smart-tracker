import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";

import {
  getBudget,
  createBudget,
  deleteBudget
} from "../services/budgetService";
import "./Budget.css";

function Budget() {
  const [budgets, setBudgets] = useState([]);
  const [category, setCategory] = useState("");
  const [limitAmount, setLimitAmount] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // 🔹 Fetch all budgets
  const fetchBudgets = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await getBudget();
      setBudgets(res.data);
    } catch (err) {
  if (err.response && err.response.data) {
    setError(
      typeof err.response.data === "string"
        ? err.response.data
        : err.response.data.message || err.response.data.error
    );
  } else {
    setError("Something went wrong");
  }
} finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBudgets();
  }, []);

  // 🔹 Create Budget
  const handleSave = async (e) => {
    e.preventDefault();

    if (!category || !limitAmount || !startDate || !endDate) {
      setError("All fields are required");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const payload = {
        category,
        limitAmount: Number(limitAmount),
        startDate,
        endDate
      };

      await createBudget(payload);

      // Reset form
      setCategory("");
      setLimitAmount("");
      setStartDate("");
      setEndDate("");

      fetchBudgets();
    } catch (err) {
      setError(err?.response?.data.error || "Failed to create budget");
    } finally {
      setLoading(false);
    }
  };

  // 🔹 Delete Budget
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this budget?"))
      return;

    try {
      await deleteBudget(id);
      fetchBudgets();
    } catch (err) {
      alert("Failed to delete budget");
    }
  };

 return (
  <div className="d-flex">
    <Sidebar />

    <div className="flex-grow-1" style={{ marginLeft: "280px" }}>
      <Navbar /> 

      <div className="container-fluid py-4">

       

        {/* 🔥 Create Budget Form */}
        <div className="card shadow border-0 rounded-4 mb-5">
          <div className="card-body p-4">
            <h4 className="card-title mb-4 text-primary">
              Create Budget
            </h4>

            {error && (
              <div className="alert alert-danger">
                {error}
              </div>
            )}

            <form onSubmit={handleSave}>
              <div className="row g-4">

                {/* Category */}
                <div className="col-md-6">
                  <label className="form-label fw-semibold">
                    Category
                  </label>
                  <input
                    type="text"
                    className="form-control shadow-sm"
                    placeholder="Enter category (e.g. Food)"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                  />
                </div>

                {/* Limit Amount */}
                <div className="col-md-6">
                  <label className="form-label fw-semibold">
                    Limit Amount
                  </label>
                  <input
                    type="number"
                    className="form-control shadow-sm"
                    placeholder="Enter budget amount"
                    value={limitAmount}
                    onChange={(e) => setLimitAmount(e.target.value)}
                  />
                </div>

                {/* Start Date */}
                <div className="col-md-6">
                  <label className="form-label fw-semibold">
                    Start Date
                  </label>
                  <input
                    type="date"
                    className="form-control shadow-sm"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                  />
                </div>

                {/* End Date */}
                <div className="col-md-6">
                  <label className="form-label fw-semibold">
                    End Date
                  </label>
                  <input
                    type="date"
                    className="form-control shadow-sm"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                  />
                </div>

                {/* Submit Button */}
                <div className="col-12 text-end">
                  <button
                    type="submit"
                    className="btn btn-primary px-4 rounded-pill"
                  >
                    {loading ? "Saving..." : "Save Budget"}
                  </button>
                </div>

              </div>
            </form>
          </div>
        </div>

        {/* 🔥 Budget Cards List */}
        <div className="row">
          {loading && (
            <div className="col-12 text-center">
              <div className="spinner-border text-primary"></div>
            </div>
          )}

          {!loading && budgets.length === 0 && (
            <div className="col-12 text-center text-muted">
              No budgets created yet.
            </div>
          )}

          {budgets.map((budget) => (
            <div
              key={budget.id || budget._id}
              className="col-md-4 col-lg-3 mb-4"
            >
              <div className="card shadow-sm border-0 rounded-4 h-100">
                <div className="card-body">
                  <h5 className="text-primary fw-bold">
                    {budget.category} Budget
                  </h5>

                  <p className="mb-1">
                    <strong>Limit:</strong> ₹{budget.limitAmount}
                  </p>

                  <p className="text-muted small">
                    {budget.startDate} → {budget.endDate}
                  </p>

                  <div className="d-flex justify-content-end">
                    <button
                      className="btn btn-outline-danger btn-sm rounded-pill"
                      onClick={() =>
                        handleDelete(budget.id || budget._id)
                      }
                    >
                      Delete
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

      </div>
    </div>
  </div>
);
}

export default Budget;