import { Navigate } from "react-router-dom";

export default function FarmerProtectedRoute({ children }) {
  const token = localStorage.getItem("token");
  const hasFarmerProfile =
    localStorage.getItem("hasFarmerProfile") === "true";

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  if (!hasFarmerProfile) {
    return <Navigate to="/profile/setup" replace />;
  }

  return children;
}
