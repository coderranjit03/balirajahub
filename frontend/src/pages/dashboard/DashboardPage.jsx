import {
  CloudSun,
  IndianRupee,
  Bell,
  Sprout,
  CalendarDays,
} from "lucide-react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import DashboardLayout from "../../components/layout/DashboardLayout";
import AiAdvisor from "../../components/ai/AiAdvisor";

import { getDashboardStats } from "../../services/dashboardService";
import { getCurrentWeather } from "../../services/weatherService";
import { getFarmerProfile } from "../../services/farmerProfileService";
import { getUpcomingReminders } from "../../services/reminderService";

function StatCard({ icon: Icon, title, value, color }) {
  return (
    <div className="group rounded-3xl border border-white/40 bg-white/80 p-6 shadow-lg backdrop-blur-md transition-all duration-300 hover:-translate-y-1 hover:shadow-2xl">
      <div
        className={`mb-4 flex h-14 w-14 items-center justify-center rounded-2xl ${color}`}
      >
        <Icon size={28} className="text-white" />
      </div>

      <p className="text-sm font-medium text-slate-500">{title}</p>

      <h3 className="mt-2 text-3xl font-bold text-slate-900">
        {value}
      </h3>
    </div>
  );
}

export default function DashboardPage() {
  const [stats, setStats] = useState({
    totalCrops: 0,
    totalDiaryEntries: 0,
    totalExpenseAmount: 0,
    pendingReminders: 0,
  });

  const [upcomingReminders, setUpcomingReminders] = useState([]);

  const [farmerName, setFarmerName] = useState("Farmer");

  const [farmerLocation, setFarmerLocation] = useState({
    village: "Mumbai",
    district: "Mumbai",
  });

  const [weather, setWeather] = useState({
    temperature: "--",
    humidity: "--",
    condition: "Loading weather...",
  });

  const getGreeting = () => {
    const hour = new Date().getHours();

    if (hour >= 5 && hour < 12) return "Good Morning 🌅";
    if (hour >= 12 && hour < 17) return "Good Afternoon ☀️";
    if (hour >= 17 && hour < 21) return "Good Evening 🌇";

    return "Good Night 🌙";
  };

  useEffect(() => {
    const loadDashboardData = async () => {
      try {
        // 1. Farmer profile
        const profile = await getFarmerProfile();

        setFarmerName(profile.firstName || "Farmer");

        setFarmerLocation({
          village: profile.village,
          district: profile.district,
        });

        // 2. Weather
        if (profile.district) {
          const weatherData = await getCurrentWeather(profile.district);
          setWeather(weatherData);
        }

        // 3. Dashboard stats
        const dashboardStats = await getDashboardStats();
        setStats(dashboardStats);

        // 4. Upcoming reminders
        const upcoming = await getUpcomingReminders();
        setUpcomingReminders(upcoming);

      } catch (error) {
        console.error("Dashboard load failed:", error);
      }
    };

    loadDashboardData();
  }, []);

  return (
    <DashboardLayout>
      <div className="min-h-screen overflow-hidden p-6">

        {/* Decorative Background */}
        <div className="pointer-events-none absolute left-10 top-20 text-5xl opacity-10 animate-bounce">
          🌿
        </div>

        <div className="pointer-events-none absolute right-16 top-40 text-6xl opacity-10 animate-pulse">
          🍃
        </div>

        <main className="relative mx-auto max-w-7xl py-2">

          {/* Welcome Section */}
          <div className="relative mb-8 overflow-hidden rounded-[2rem] bg-gradient-to-r from-emerald-700 via-green-600 to-lime-500 p-8 text-white shadow-2xl shadow-emerald-600/20">

            <div className="absolute -right-10 -top-10 h-40 w-40 rounded-full bg-white/10"></div>

            <div className="absolute -bottom-16 right-32 h-56 w-56 rounded-full bg-white/5"></div>

            <div className="relative flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">

              <div className="max-w-2xl">

                <p className="text-sm font-semibold uppercase tracking-wide text-emerald-100">
                  {getGreeting()}
                </p>

                <h2 className="mt-3 text-4xl font-bold leading-tight lg:text-5xl">
                  Welcome back, {farmerName} 👨‍🌾
                </h2>

                <p className="mt-4 text-lg leading-relaxed text-emerald-50">
                  Your farm is looking healthy today. Stay updated with weather
                  conditions, crop activities, and AI-powered farming suggestions
                  tailored for your region.
                </p>
              </div>

              {/* Weather Card */}
              <div className="w-full max-w-sm rounded-3xl border border-white/20 bg-white/15 p-6 backdrop-blur-md shadow-lg">

                <div className="flex items-center gap-3">

                  <CloudSun size={30} className="text-yellow-200" />

                  <div>

                    <p className="text-sm text-emerald-100">
                      Today's Weather
                    </p>

                    <p className="text-lg font-semibold text-white">
                      📍 {farmerLocation.village}, {farmerLocation.district}
                    </p>
                  </div>
                </div>

                <div className="mt-4 flex items-end gap-2">

                  <p className="text-5xl font-bold text-white">
                    {weather.temperature}
                  </p>

                  <span className="mb-1 text-xl font-semibold text-emerald-100">
                    °C
                  </span>
                </div>

                <p className="mt-3 text-sm text-emerald-50">
                  {weather.condition}
                </p>

                <div className="mt-4 flex items-center justify-between rounded-2xl bg-white/10 px-4 py-3 text-sm text-emerald-50">

                  <span>Humidity</span>

                  <span className="font-semibold text-white">
                    {weather.humidity}%
                  </span>
                </div>
              </div>
            </div>
          </div>

          {/* Stats */}
          <div className="grid gap-6 sm:grid-cols-2 xl:grid-cols-4">

            <StatCard
              icon={Sprout}
              title="Total Crops"
              value={stats.totalCrops}
              color="bg-emerald-500"
            />

            <StatCard
              icon={IndianRupee}
              title="Total Expenses"
              value={`₹${Number(stats.totalExpenseAmount ?? 0).toLocaleString()}`}
              color="bg-green-500"
            />

            <StatCard
              icon={Bell}
              title="Pending Reminders"
              value={stats.pendingReminders ?? 0}
              color="bg-amber-500"
            />

            <StatCard
              icon={CalendarDays}
              title="Farm Diary Entries"
              value={stats.totalDiaryEntries}
              color="bg-lime-500"
            />
          </div>

          {/* Quick Actions */}
          <div className="mt-8 flex flex-wrap gap-4">

            <Link
              to="/crops"
              className="inline-flex items-center gap-2 rounded-2xl bg-emerald-600 px-6 py-3 font-semibold text-white shadow-lg transition-all duration-300 hover:-translate-y-0.5 hover:bg-emerald-700 hover:shadow-emerald-600/30"
            >
              🌱 Manage Crops
            </Link>

            <Link
              to="/expenses"
              className="inline-flex items-center gap-2 rounded-2xl bg-white/80 px-6 py-3 font-semibold text-emerald-700 shadow-md backdrop-blur-md transition-all duration-300 hover:-translate-y-0.5 hover:bg-white hover:shadow-lg"
            >
              💰 Add Expense
            </Link>

            <Link
              to="/reminders"
              className="inline-flex items-center gap-2 rounded-2xl bg-white/80 px-6 py-3 font-semibold text-emerald-700 shadow-md backdrop-blur-md transition-all duration-300 hover:-translate-y-0.5 hover:bg-white hover:shadow-lg"
            >
              📅 Add Reminder
            </Link>

            <Link
              to="/farm-diary"
              className="inline-flex items-center gap-2 rounded-2xl bg-white/80 px-6 py-3 font-semibold text-emerald-700 shadow-md backdrop-blur-md transition-all duration-300 hover:-translate-y-0.5 hover:bg-white hover:shadow-lg"
            >
              📒 Farm Diary
            </Link>

          </div>

          {/* Bottom Section */}
          <div className="mt-8 grid gap-6 lg:grid-cols-3">

            {/* AI Advisor */}
            <div className="lg:col-span-2">
              <AiAdvisor />
            </div>

            {/* Upcoming Tasks */}
            <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">

              <div className="mb-5 flex items-center gap-3">

                <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-amber-100 text-amber-700">
                  <Bell size={24} />
                </div>

                <div>

                  <h3 className="text-xl font-bold text-slate-900">
                    Upcoming Tasks
                  </h3>

                  <p className="text-sm text-slate-500">
                    Your next pending reminders
                  </p>
                </div>
              </div>

              {upcomingReminders.length > 0 ? (
                <div className="space-y-4">
                  {upcomingReminders.map((reminder) => (
                    <div
                      key={reminder.id}
                      className="rounded-2xl border border-amber-100 bg-amber-50 p-4 transition hover:shadow-md"
                    >
                      <div className="flex items-start justify-between gap-3">
                        <div>
                          <p className="font-semibold text-amber-700">
                            {reminder.title}
                          </p>

                          <p className="mt-1 text-xs font-medium text-slate-500">
                            🌱 {reminder.cropName || "General Reminder"}
                          </p>

                          {reminder.description && (
                            <p className="mt-1 text-sm text-slate-600">
                              {reminder.description}
                            </p>
                          )}

                          <p className="mt-2 text-xs text-slate-500">
                            📅 {reminder.reminderDate}
                          </p>
                        </div>

                        <span className="text-lg">🔔</span>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="rounded-2xl border border-dashed border-amber-200 bg-white/70 p-8 text-center">
                  <div className="text-4xl">📅</div>

                  <h4 className="mt-3 text-lg font-semibold text-slate-800">
                    No upcoming reminders
                  </h4>

                  <p className="mt-1 text-sm text-slate-500">
                    Add reminders to keep track of important farming activities.
                  </p>

                  <Link
                    to="/reminders"
                    className="mt-4 inline-flex items-center gap-2 rounded-xl bg-emerald-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-emerald-700"
                  >
                    Add Reminder
                  </Link>
                </div>
              )}
            </div>
          </div>

          {/* Footer */}
          <div className="mt-10 text-center text-sm text-slate-500">
            Built with 💚 for Indian Farmers • BalirajaHub
          </div>
        </main>
      </div>
    </DashboardLayout>
  );
}
