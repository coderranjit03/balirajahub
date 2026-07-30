import { Link } from "react-router-dom";
import { Sprout, CloudSun, Bot, IndianRupee } from "lucide-react";

export default function LandingPage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-emerald-50 via-green-50 to-lime-50">
      {/* Navbar */}
      <header className="sticky top-0 z-50 border-b border-white/20 bg-white/70 backdrop-blur-xl">
        <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4">
          <div className="flex items-center gap-3">
            <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-emerald-600 text-white shadow-lg">
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

          <div className="flex items-center gap-3">
            <Link
              to="/login"
              className="rounded-2xl border border-emerald-600 px-4 py-2 text-sm font-semibold text-emerald-700 transition hover:bg-emerald-50"
            >
              Login
            </Link>

            <Link
              to="/register"
              className="rounded-2xl bg-emerald-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-emerald-700"
            >
              Get Started
            </Link>
          </div>
        </div>
      </header>

      {/* Hero */}
      <section className="mx-auto max-w-7xl px-6 py-20">
        <div className="grid items-center gap-12 lg:grid-cols-2">
          <div>
            <p className="text-sm font-semibold uppercase tracking-wide text-emerald-600">
              🌱 Digital Agriculture for Every Farmer
            </p>

            <h2 className="mt-4 text-5xl font-bold leading-tight text-slate-900 lg:text-6xl">
              Smart Farming Starts with
              <span className="text-emerald-600"> BalirajaHub</span>
            </h2>

            <p className="mt-6 text-lg leading-relaxed text-slate-600">
              Manage crops, track expenses, get weather updates, and receive
              AI-powered farming advice tailored for Indian farmers.
            </p>

            <div className="mt-8 flex flex-wrap gap-4">
              <Link
                to="/register"
                className="rounded-2xl bg-emerald-600 px-6 py-3 font-semibold text-white shadow-lg transition hover:-translate-y-0.5 hover:bg-emerald-700"
              >
                Create Free Account 🌾
              </Link>

              <Link
                to="/login"
                className="rounded-2xl border border-emerald-600 px-6 py-3 font-semibold text-emerald-700 transition hover:bg-emerald-50"
              >
                Farmer Login
              </Link>
            </div>
          </div>

          <div className="relative">
            <div className="rounded-[2rem] bg-gradient-to-br from-emerald-600 via-green-600 to-lime-500 p-8 text-white shadow-2xl">
              <div className="grid gap-4 sm:grid-cols-2">
                <div className="rounded-2xl bg-white/15 p-4 backdrop-blur-md">
                  <CloudSun className="mb-3 text-yellow-200" size={32} />
                  <p className="text-sm text-emerald-100">Live Weather</p>
                  <p className="mt-1 text-2xl font-bold">29°C</p>
                </div>

                <div className="rounded-2xl bg-white/15 p-4 backdrop-blur-md">
                  <Sprout className="mb-3 text-lime-100" size={32} />
                  <p className="text-sm text-emerald-100">Crop Tracking</p>
                  <p className="mt-1 text-2xl font-bold">5 Crops</p>
                </div>

                <div className="rounded-2xl bg-white/15 p-4 backdrop-blur-md">
                  <Bot className="mb-3 text-emerald-100" size={32} />
                  <p className="text-sm text-emerald-100">AI Advisor</p>
                  <p className="mt-1 text-2xl font-bold">24/7</p>
                </div>

                <div className="rounded-2xl bg-white/15 p-4 backdrop-blur-md">
                  <IndianRupee className="mb-3 text-emerald-100" size={32} />
                  <p className="text-sm text-emerald-100">Expense Tracking</p>
                  <p className="mt-1 text-2xl font-bold">Smart</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="mx-auto max-w-7xl px-6 pb-20">
        <div className="text-center mb-12">
          <h3 className="text-3xl font-bold text-slate-900">
            Everything a Modern Farmer Needs
          </h3>

          <p className="mt-3 text-slate-600">
            Simple tools designed for real farming workflows.
          </p>
        </div>

        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
          {[
            {
              icon: Sprout,
              title: "Crop Management",
              desc: "Add crops, monitor sowing dates, and plan harvests efficiently.",
            },
            {
              icon: CloudSun,
              title: "Weather Updates",
              desc: "Get village and district-wise weather information for better decisions.",
            },
            {
              icon: Bot,
              title: "AI Farming Advisor",
              desc: "Receive intelligent suggestions for crop health and farm productivity.",
            },
            {
              icon: IndianRupee,
              title: "Expense Tracking",
              desc: "Track farming expenses and understand monthly spending patterns.",
            },
          ].map((feature) => {
            const Icon = feature.icon;

            return (
              <div
                key={feature.title}
                className="rounded-3xl border border-white/40 bg-white/80 p-6 shadow-lg backdrop-blur-md transition hover:-translate-y-1 hover:shadow-2xl"
              >
                <div className="mb-4 flex h-14 w-14 items-center justify-center rounded-2xl bg-emerald-100 text-emerald-700">
                  <Icon size={28} />
                </div>

                <h4 className="text-lg font-bold text-slate-900">
                  {feature.title}
                </h4>

                <p className="mt-2 text-sm leading-relaxed text-slate-600">
                  {feature.desc}
                </p>
              </div>
            );
          })}
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-emerald-100 bg-white/60 py-6 text-center text-sm text-slate-500 backdrop-blur-md">
        Built with 💚 for Indian Farmers • BalirajaHub
      </footer>
    </div>
  );
}
