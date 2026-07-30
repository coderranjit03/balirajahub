import { Link, useLocation, useNavigate } from "react-router-dom";
import {
  LayoutDashboard,
  Sprout,
  User,
  LogOut,
} from "lucide-react";

export default function DashboardLayout({ children }) {
  const location = useLocation();
  const navigate = useNavigate();

  const menuItems = [
    {
      name: "Dashboard",
      path: "/dashboard",
      icon: LayoutDashboard,
    },
    {
      name: "My Crops",
      path: "/crops",
      icon: Sprout,
    },
    {
      name: "My Profile",
      path: "/profile",
      icon: User,
    },
  ];

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("hasFarmerProfile");
    navigate("/login");
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-emerald-50 via-green-50 to-lime-50">
      {/* Fixed Sidebar */}
      <aside className="fixed left-0 top-0 z-50 flex h-screen w-80 flex-col border-r border-emerald-100 bg-white shadow-lg">
        {/* Logo */}
        <div className="border-b border-emerald-100 p-6">
          <div className="flex items-center gap-4">
            <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-emerald-600 text-white shadow-lg">
              🌾
            </div>

            <div>
              <h1 className="text-2xl font-bold text-emerald-700">
                BalirajaHub
              </h1>

              <p className="text-sm text-slate-500">
                Smart Farming Platform
              </p>
            </div>
          </div>
        </div>

        {/* Navigation */}
        <nav className="flex-1 space-y-2 p-4 overflow-y-auto">
          {menuItems.map((item) => {
            const Icon = item.icon;
            const active = location.pathname === item.path;

            return (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center gap-3 rounded-2xl px-5 py-4 text-base font-medium transition-all duration-300 ${
                  active
                    ? "bg-emerald-600 text-white shadow-lg shadow-emerald-600/30"
                    : "text-slate-700 hover:bg-emerald-50 hover:text-emerald-700"
                }`}
              >
                <Icon size={22} />
                {item.name}
              </Link>
            );
          })}
        </nav>

        {/* Logout */}
        <div className="border-t border-emerald-100 p-4">
          <button
            onClick={handleLogout}
            className="flex w-full items-center gap-3 rounded-2xl px-5 py-4 text-base font-medium text-red-600 transition hover:bg-red-50"
          >
            <LogOut size={22} />
            Logout
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="ml-80 min-h-screen overflow-y-auto p-6 lg:p-8">
        {children}
      </main>
    </div>
  );
}
