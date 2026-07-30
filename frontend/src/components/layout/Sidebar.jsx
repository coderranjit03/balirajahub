import {
  LayoutDashboard,
  Sprout,
  User,
  LogOut,
} from "lucide-react";
import { NavLink, useNavigate } from "react-router-dom";

export default function Sidebar() {

  const navigate = useNavigate();

  const logout = () => {

    localStorage.removeItem("token");
    localStorage.removeItem("hasFarmerProfile");

    navigate("/login");
  };

  const linkClass = ({ isActive }) =>
    `flex items-center gap-3 rounded-2xl px-4 py-3 text-sm font-medium transition ${
      isActive
        ? "bg-emerald-600 text-white shadow-lg"
        : "text-slate-700 hover:bg-emerald-50 hover:text-emerald-700"
    }`;

  return (
    <aside className="hidden md:flex h-screen w-72 flex-col border-r border-emerald-100 bg-white/90 backdrop-blur-xl">

      {/* Logo */}
      <div className="flex items-center gap-3 border-b border-emerald-100 px-6 py-5">

        <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-emerald-600 text-white shadow-lg">
          🌾
        </div>

        <div>
          <h1 className="text-xl font-bold text-emerald-700">
            BalirajaHub
          </h1>

          <p className="text-xs text-slate-500">
            Smart Farming Platform
          </p>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 space-y-2 px-4 py-6">

        <NavLink to="/dashboard" className={linkClass}>
          <LayoutDashboard size={20} />
          Dashboard
        </NavLink>

        <NavLink to="/crops" className={linkClass}>
          <Sprout size={20} />
          My Crops
        </NavLink>

        <NavLink to="/profile" className={linkClass}>
          <User size={20} />
          My Profile
        </NavLink>

      </nav>

      {/* Logout */}
      <div className="border-t border-emerald-100 p-4">

        <button
          onClick={logout}
          className="flex w-full items-center gap-3 rounded-2xl px-4 py-3 text-sm font-medium text-red-600 transition hover:bg-red-50"
        >
          <LogOut size={20} />
          Logout
        </button>

      </div>
    </aside>
  );
}