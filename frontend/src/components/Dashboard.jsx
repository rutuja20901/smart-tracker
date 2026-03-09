import { useEffect, useState } from "react";
import DashboardCard from "./DashboardCard";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import './Dashboard.css';
import { getDashboardSummary } from "../services/dashboardService";
import { Bar, Pie } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend);

function DashboardPage() {
  const [data, setData] = useState({
    totalExpense: 0,
    totalBudget: 0,
    remainingAmount: 0,
    budgetExceeded: false,
    exceededAmount: 0
  });
  const [categoryDataState, setCategoryDataState] = useState(null);
  const [monthlyDataState, setMonthlyDataState] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      setError(null);
      try {
        // pass current month & year to match Postman request that succeeds
        const now = new Date();
        const month = now.getMonth() + 1; // JS months are 0-indexed
        const year = now.getFullYear();
        const resp = await getDashboardSummary({ month, year });
        console.debug('getDashboardSummary response:', resp);
        const d = resp.data || {};

        // Map backend fields to frontend model. Backend may use different names
        // (e.g. `totalMonthlyExpense` or `total_monthly_expense`) so we accept
        // multiple variants here and normalize to the frontend shape.
        setData(prev => ({
          ...prev,
          // Backend returns `totalMonthlyExpense` and `budgetLimit`.
          totalExpense: d.totalMonthlyExpense ?? d.totalExpense ?? d.totalExpenseAmount ?? d.total_monthly_expense ?? 0,
          totalBudget: d.budgetLimit ?? d.totalBudget ?? d.totalBudgetAmount ?? d.total_budget ?? 0,
          // Use backend remainingAmount when provided
          remainingAmount: d.remainingAmount ?? d.remaining_amount ?? ( (d.budgetLimit ?? d.totalBudget ?? 0) - (d.totalMonthlyExpense ?? d.totalExpense ?? 0) ),
          // Map flags/amounts
          budgetExceeded: d.budgetExceeded ?? d.isBudgetExceeded ?? false,
          exceededAmount: d.exceededAmount ?? d.exceeded_amount ?? 0,
        }));

        // category breakdown: accept multiple shapes
        if (d.categoryBreakdown && Array.isArray(d.categoryBreakdown)) {
          const labels = d.categoryBreakdown.map(c => c.name ?? c.category ?? c.label);
          const values = d.categoryBreakdown.map(c => c.amount ?? c.value ?? 0);
          setCategoryDataState({ labels, datasets: [{ data: values, backgroundColor: ['#ef4444', '#f59e0b', '#3b82f6', '#10b981', '#8b5cf6'] }] });
        } else if (d.categories && Array.isArray(d.categories)) {
          const labels = d.categories.map(c => c.name || c);
          const values = d.categories.map(c => c.amount ?? c.value ?? 0);
          setCategoryDataState({ labels, datasets: [{ data: values, backgroundColor: ['#ef4444', '#f59e0b', '#3b82f6', '#10b981', '#8b5cf6'] }] });
        }

        // monthly trend: accept {month, amount} or arrays
        if (d.monthlyTrend && Array.isArray(d.monthlyTrend)) {
          const labels = d.monthlyTrend.map(m => m.month ?? m.label ?? m.name);
          const values = d.monthlyTrend.map(m => m.amount ?? m.value ?? 0);
          setMonthlyDataState({ labels, datasets: [{ label: 'Expense', data: values, backgroundColor: '#3b82f6' }] });
        }

      } catch (err) {
        console.error('Dashboard fetch error', err);
       
        const status = err?.response?.status;
        const respData = err?.response?.data;
        setError(`HTTP ${status || ''} ${err?.message || ''} ${respData ? JSON.stringify(respData) : ''}`);
      } finally {
        setLoading(false);
      }
    };

    fetch();
  }, []);

  // fallback sample data (used if API doesn't provide)
  const sampleCategoryData = {
    labels: ['Food', 'Transport', 'Shopping', 'Utilities', 'Other'],
    datasets: [
      { data: [7000, 4000, 6000, 3000, 1000], backgroundColor: ['#ef4444', '#f59e0b', '#3b82f6', '#10b981', '#8b5cf6'] },
    ],
  };

  const sampleMonthlyData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
    datasets: [{ label: 'Expense', data: [2000, 2500, 2200, 2700, 3000, 3600], backgroundColor: '#3b82f6' }],
  };

  const remaining = data.remainingAmount ?? (data.totalBudget - data.totalExpense);

  return (
    <div className="dashboard-root">
      <Sidebar />

      <div className="dashboard-main">
        <Navbar />

          {data.budgetExceeded && (
          <div className="dashboard-warning">⚠ Budget Exceeded!</div>
        )}

          {loading && <div style={{ padding: 12, margin: 12 }}>Loading dashboard...</div>}
          {/* {error && <div style={{ padding: 12, margin: 12, color: 'red' }}>Error: {String(error)}</div>} */}

        <div className="dashboard-content">
          <div className="cards-grid">
            <div className="metrics-grid">
              <div className="dashboard-card-custom"><DashboardCard title="Total Expense" value={data.totalExpense} /></div>
              <div className="dashboard-card-custom"><DashboardCard title="Total Budget" value={data.totalBudget} /></div>
            </div>

            <div className="metrics-grid">
              <div className="dashboard-card-custom"><DashboardCard title="Remaining" value={remaining} /></div>
              <div className="dashboard-card-custom"><DashboardCard title="Exceeded Amount" value={data.exceededAmount} /></div>
            </div>

            <div className="chart-card">
              <h3>Expense by Category (Pie Chart)</h3>
              <div className="chart-container pie-chart">
                  <Pie
                    data={categoryDataState ?? sampleCategoryData}
                    options={{
                      responsive: true,
                      maintainAspectRatio: false,
                      plugins: {
                        legend: { position: 'right', labels: { boxWidth: 12 } },
                        tooltip: { mode: 'nearest' },
                      },
                      layout: { padding: 12 },
                    }}
                  />
              </div>
            </div>

            <div className="chart-card">
              <h3>Monthly Expense Trend (Bar Chart)</h3>
              <div className="chart-container bar-chart">
                <Bar
                  data={{
                    ...(monthlyDataState ?? sampleMonthlyData),
                    datasets: (monthlyDataState ?? sampleMonthlyData).datasets.map(ds => ({ ...ds, maxBarThickness: 40 }))
                  }}
                  options={{
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                      legend: { display: false },
                      tooltip: { mode: 'index', intersect: false },
                    },
                    scales: { y: { beginAtZero: true, ticks: { precision: 0 } }, x: { ticks: { maxRotation: 0 } } },
                    layout: { padding: 12 },
                  }}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

const warningStyle = {
  background: "#fee2e2",
  color: "#b91c1c",
  padding: "10px",
  margin: "20px",
  borderRadius: "5px"
};

export default DashboardPage;