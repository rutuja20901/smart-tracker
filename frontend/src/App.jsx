import {  Route, Routes, Navigate } from "react-router-dom";

import MainLayout from "./layouts/MainLayout";
import Login from "./pages/Login";
import Register from "./pages/Register";
import DashboardPage from "./pages/DashboardPage"; 
import Expenses from "./pages/Expenses";
import Budget  from "./pages/Budget";
import Reports from "./pages/Reports";

import ProtectedRoute from "./routes/ProtectedRoute";

function App() {
  return (
    
    <Routes>


        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />


        <Route path="/" element={<ProtectedRoute>
            <MainLayout />
          </ProtectedRoute>}>
          <Route index element={<Navigate to="/dashboard" />} />
          <Route path="dashboard" element={<DashboardPage />} />
          <Route path="dashboard/expenses" element={<Expenses />} />
          <Route path="dashboard/budget" element={<Budget />} />
          <Route path="dashboard/reports" element={<Reports />} />
          
        </Route>
    
      

    </Routes>
    
  );
}

export default App;
