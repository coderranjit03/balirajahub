import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { Toaster } from "react-hot-toast";

import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import DashboardPage from "./pages/dashboard/DashboardPage";
import ProtectedRoute from "./components/ProtectedRoute";
import CropsPage from "./pages/crops/CropsPage";
import ProfileSetupPage from "./pages/profile/ProfileSetupPage";
import ProfilePage from "./pages/profile/ProfilePage";
import LandingPage from "./pages/public/LandingPage";
import FarmDiaryPage from "./pages/farm-diary/FarmDiaryPage";
import ExpensesPage from "./pages/expenses/ExpensesPage";
import RemindersPage from "./pages/reminders/RemindersPage";

export default function App() {
  return (
    <BrowserRouter>
      <Toaster position="top-right" />

      <Routes>
        <Route path="/" element={<LandingPage />} />

        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/crops"
          element={
            <ProtectedRoute>
              <CropsPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/profile/setup"
          element={
            <ProtectedRoute>
              <ProfileSetupPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <ProfilePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/farm-diary"
          element={
            <ProtectedRoute>
              <FarmDiaryPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/expenses"
          element={
            <ProtectedRoute>
              <ExpensesPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/reminders"
          element={
            <ProtectedRoute>
              <RemindersPage />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}
