import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import DashboardCard from "../components/DashboardCard";
import "./Reports.css";
import { getSummary, getCategoryWise } from "../services/reportService";
import { Pie } from "react-chartjs-2";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

function Reports() {
  const now = new Date();

  const [month, setMonth] = useState(now.getMonth() + 1);
  const [year, setYear] = useState(now.getFullYear());

   const [monthlyReport, setMonthlyReport] = useState(null);
  const [categoryReport, setCategoryReport] = useState([]);
  const [categoryData, setCategoryData] = useState(null);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);


  const fetchAll = async () => {
    setLoading(true);
    setError(null);

    try {
      const params = { month, year };

      // Monthly API
      const monthlyResp = await getSummary(params);
      setMonthlyReport(monthlyResp.data ?? {});

      console.log("Monthly from API:", monthlyResp.data);
      // Category API
      const categoryResp = await getCategoryWise(params);
      const cats = Array.isArray(categoryResp.data)
        ? categoryResp.data
        : [];

      setCategoryReport(cats);

      // Prepare Pie Chart Data
      const labels = cats.map((c) => c.category);
      const values = cats.map((c) => c.totalmount ?? 0);

      setCategoryData({
        labels,
        datasets: [
          {
            data: values,
            backgroundColor: [
              "#ef4444",
              "#f59e0b",
              "#3b82f6",
              "#10b981",
              "#8b5cf6",
            ],
          },
        ],
      });
    } catch (err) {
      console.error("Reports fetch error", err);
      setError("Failed to fetch report");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAll();
  }, [month, year]);

  const handleDownload = () => {
    window.print();
  };

// ✅ MONTH NAMES MUST BE HERE
  const monthNames = [
    "January","February","March","April",
    "May","June","July","August",
    "September","October","November","December"
  ];

  return (
    <div className="reports-root">
      <Sidebar />

      <div className="reports-main">
        <Navbar />

        <div className="reports-content">
         

          {/* Filters */}
          <div className="reports-filters">
            <div>
              <select
                value={month}
                onChange={(e) => setMonth(Number(e.target.value))}
              >
                {Array.from({ length: 12 }).map((_, i) => (
                  <option key={i + 1} value={i + 1}>
                    {i + 1}
                  </option>
                ))}
              </select>

              <select
                value={year}
                onChange={(e) => setYear(Number(e.target.value))}
              >
                {Array.from({ length: 5 }).map((_, i) => {
                  const y = now.getFullYear() - i;
                  return (
                    <option key={y} value={y}>
                      {y}
                    </option>
                  );
                })}
              </select>
            </div>

            <div>
              <button className="primary" onClick={fetchAll}>
                Fetch
              </button>
              <button
                className="ghost"
                onClick={handleDownload}
                style={{ marginLeft: 8 }}
              >
                Download PDF
              </button>
            </div>
          </div>

          {loading && <div>Loading...</div>}
          {error && <div style={{ color: "red" }}>{error}</div>}

          {/* Summary Cards */}
          <div className="summary-cards">
            <DashboardCard
              title="Total Expense"
              value={monthlyReport?.totalExpense ?? 0}
            />

           {/* <DashboardCard
              title="Selected Month"
              value={monthlyReport
      ? `${monthNames[monthlyReport.month - 1]} ${monthlyReport.year}`
      : "—"
  }
/> */}

            <DashboardCard
              title="Categories"
              value={categoryReport?.length ?? 0}
            />
          </div>

          {/* Chart + Breakdown */}
          <div className="charts-row">
            {/* Pie Chart */}
            <div className="chart-card">
              <h4>Pie Chart</h4>
              <div className="chart-area">
                <Pie
                  data={
                    categoryData ?? {
                      labels: [],
                      datasets: [{ data: [] }],
                    }
                  }
                  options={{ maintainAspectRatio: false }}
                />
              </div>
            </div>

            {/* Expense Breakdown */}
            <div className="chart-card">
              <h4>Expense Breakdown</h4>

              {categoryReport.length === 0 ? (
                <p>No data available</p>
              ) : (
                categoryReport.map((item, index) => (
                  <div
                    key={index}
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                      padding: "8px 0",
                      borderBottom: "1px solid #eee",
                    }}
                  >
                    <span>{item.category}</span>
                    <strong>₹ {item.totalmount}</strong>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Reports;