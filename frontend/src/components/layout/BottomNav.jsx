import {
  Home,
  Sprout,
  CalendarDays,
  Bot,
  User,
} from "lucide-react";
import { Link, useLocation } from "react-router-dom";

const items = [
  { to: "/dashboard", icon: Home, label: "Home" },
  { to: "/crops", icon: Sprout, label: "Crops" },
  { to: "/reminders", icon: CalendarDays, label: "Tasks" },
  { to: "/ai", icon: Bot, label: "AI" },
  { to: "/profile", icon: User, label: "Profile" },
];

export default function BottomNav() {
  const location = useLocation();

  return (
    <nav className="fixed bottom-4 left-1/2 z-50 w-[92%] max-w-md -translate-x-1/2 rounded-3xl border border-white/30 bg-white/85 p-2 shadow-2xl backdrop-blur-xl md:hidden">
      <div className="flex items-center justify-between">
        {items.map(({ to, icon: Icon, label }) => {
          const active = location.pathname === to;

          return (
            <Link
              key={to}
              to={to}
              className={`flex flex-1 flex-col items-center gap-1 rounded-2xl px-3 py-2 text-xs font-medium transition ${
                active
                  ? "bg-emerald-100 text-emerald-700 shadow-sm"
                  : "text-slate-500 hover:bg-emerald-50 hover:text-emerald-700"
              }`}
            >
              <Icon size={20} />
              <span>{label}</span>
            </Link>
          );
        })}
      </div>
    </nav>
  );
}
