import { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import Navbar from '../components/Navbar';
import DashboardCard from '../components/DashboardCard';
import './Expenses.css';
import { getExpenses, addExpense, deleteExpense } from '../services/expenseService';

function Expenses(){
    const now = new Date();
    const [month, setMonth] = useState(now.getMonth() + 1);
    const [year, setYear] = useState(now.getFullYear());
    const [expenses, setExpenses] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [form, setForm] = useState({ amount: '', category: '', expenseDate: '', notes: '', title: '' });
    const [monthlyTotal, setMonthlyTotal] = useState(0);
    const [rawResponse, setRawResponse] = useState(null);

    // Normalize amount field - backend sometimes returns an object for amount
    const resolveAmount = (exp) => {
        const a = exp?.amount ?? exp?.value ?? exp?.total ?? 0;
        if (a && typeof a === 'object') return a.amount ?? a.value ?? a.total ?? 0;
        return a;
    };

    const fetchList = async () => {
        setLoading(true);
        setError(null);
        try {
            const resp = await getExpenses({ month, year });
            console.debug('GET /api/user/expense response:', resp);
            const d = resp.data;
            setRawResponse(JSON.stringify(resp.data ?? resp, null, 2));
            // Handle API that returns either an array of expenses or an object wrapper
            let list = [];
            if (Array.isArray(d)) {
                list = d;
            } else if (d) {
                list = d.expenses ?? d.data ?? d.items ?? [];
            }
            setExpenses(Array.isArray(list) ? list : []);
            // Normalize totalMonthlyExpense which may be an object
            const totalRaw = d.totalMonthlyExpense ?? d.totalMonthlyAmount ?? d.total ?? null;
            const normalizedTotalFromField = (() => {
                if (totalRaw == null) return null;
                if (typeof totalRaw === 'object') return Number(totalRaw.amount ?? totalRaw.value ?? totalRaw.total ?? 0);
                return Number(totalRaw);
            })();
            const total = normalizedTotalFromField ?? (Array.isArray(list) ? list.reduce((s, e) => s + Number(resolveAmount(e) || 0), 0) : 0);
            setMonthlyTotal(total ?? 0);
        } catch (err) {
            console.error('GET /api/user/expense error:', err?.response ?? err);
            const serverData = err?.response?.data;
            const message = serverData ? (typeof serverData === 'string' ? serverData : JSON.stringify(serverData)) : (err?.message ?? 'Failed to load expenses');
            setError(message);
            setRawResponse(serverData ? JSON.stringify(serverData, null, 2) : null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchList(); }, [month, year]);

    const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

    const handleAdd = async (e) => {
        e.preventDefault();
        try {
            await addExpense({
                amount: parseFloat(form.amount),
                category: form.category,
                expenseDate: form.expenseDate || new Date().toISOString().slice(0,10),
                notes: form.notes,
                title: form.title,
            });
            setForm({ amount: '', category: '', date: '', description: '' });
            setShowForm(false);
            fetchList();
        } catch (err) {
            setError(err?.response?.data ?? err.message ?? 'Failed to add expense');
        }
    };

    const handleDelete = async (id) => {
        if (!confirm('Delete this expense?')) return;
        try {
            await deleteExpense(id);
            fetchList();
        } catch (err) {
            setError(err?.response?.data ?? err.message ?? 'Failed to delete');
        }
    };

    return (
        <div style={{ display: 'flex' }}>
            <Sidebar />
            <div style={{ flex: 1, marginLeft: 280 }}>
                <Navbar />
                <div style={{ padding: 20 }}>
                    <div className="page-header">
                       
                        <div className="controls">
                            <button className="primary" onClick={() => setShowForm(s => !s)} style={{ marginRight: 8 }}>Add Expense</button>
                            <select value={month} onChange={e => setMonth(Number(e.target.value))}>
                                {Array.from({length:12}).map((_,i)=> <option key={i+1} value={i+1}>{i+1}</option>)}
                            </select>
                            <select value={year} onChange={e => setYear(Number(e.target.value))} style={{ marginLeft: 8 }}>
                                {Array.from({length:5}).map((_,i)=> { const y = now.getFullYear()-i; return <option key={y} value={y}>{y}</option> })}
                            </select>
                        </div>
                    </div>

                    <div className="expenses-row" style={{ marginBottom: 16 }}>
                        <div className="monthly-card">
                            <DashboardCard title="Monthly Total" value={monthlyTotal} />
                        </div>
                        <div className="expenses-table-wrapper">
                            {loading && <div>Loading...</div>}
                            {error && <div style={{ color: 'red' }}><pre style={{margin:0,whiteSpace:'pre-wrap'}}>{error}</pre></div>}
                            <table className="expenses">
                                <thead>
                                    <tr style={{ textAlign: 'left', borderBottom: '1px solid #ddd' }}>
                                        <th style={{ width: 120 }}>ID</th>
                                        <th>Date</th>
                                        <th>Category</th>
                                        <th>Title</th>
                                        <th>Notes</th>
                                        <th style={{ textAlign: 'right' }}>Amount</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {(expenses.length === 0) && <tr><td colSpan={7}>No expenses</td></tr>}
                                    {expenses.map(exp => (
                                        <tr key={exp.id ?? exp._id ?? Math.random()} style={{ borderBottom: '1px solid #f0f0f0' }}>
                                            <td style={{ fontSize: 12, color: '#6b7280' }}>{exp.id ?? exp._id ?? '—'}</td>
                                            <td>{new Date(exp.expenseDate ?? exp.date ?? exp.createdAt ?? Date.now()).toLocaleDateString()}</td>
                                            <td>{exp.category ?? exp.categoryName ?? '—'}</td>
                                            <td>{exp.title ?? exp.description ?? ''}</td>
                                            <td style={{ maxWidth: 300, whiteSpace: 'pre-wrap' }}>{exp.notes ?? exp.description ?? ''}</td>
                                            <td style={{ textAlign: 'right' }}>₹ {Number(resolveAmount(exp) ?? 0).toFixed(2)}</td>
                                            <td><button className="ghost delete-btn" onClick={() => handleDelete(exp.id ?? exp._id)}>Delete</button></td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>

                    {/* {rawResponse && (
                        <div style={{ marginTop: 12 }}>
                            <details>
                                <summary style={{ cursor: 'pointer', color: '#374151' }}>Debug: raw response (click to expand)</summary>
                                <pre style={{ maxHeight: 400, overflow: 'auto', background: '#f8fafc', padding: 12, borderRadius: 6 }}>{rawResponse}</pre>
                            </details>
                        </div>
                    )} */}

                    {showForm && (
                        <div className="modal-overlay" onClick={() => setShowForm(false)}>
                            <div className="modal-panel" onClick={(e) => e.stopPropagation()}>
                                <h3>Add Expense</h3>
                                <form onSubmit={handleAdd}>
                                    <div className="form-row">
                                        <label>Title</label>
                                        <input name="title" type="text" value={form.title} onChange={handleChange} />
                                    </div>
                                    <div className="form-row">
                                        <label>Amount</label>
                                        <input name="amount" type="number" step="0.01" value={form.amount} onChange={handleChange} required />
                                    </div>
                                    <div className="form-row">
                                        <label>Category</label>
                                        <input name="category" value={form.category} onChange={handleChange} />
                                    </div>
                                    <div className="form-row">
                                        <label>Date</label>
                                        <input name="expenseDate" type="date" value={form.expenseDate} onChange={handleChange} />
                                    </div>
                                    <div className="form-row">
                                        <label>Notes</label>
                                        <input name="notes" value={form.notes} onChange={handleChange} />
                                    </div>
                                    <div className="form-actions">
                                        <button className="primary" type="submit">Save</button>
                                        <button className="ghost" type="button" onClick={() => setShowForm(false)}>Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    )}

                </div>
            </div>
        </div>
    );
}

export default Expenses;