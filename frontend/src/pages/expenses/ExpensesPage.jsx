import { useEffect, useState } from "react";
import {
  IndianRupee,
  Plus,
  Pencil,
  Trash2,
  X,
  CalendarDays,
} from "lucide-react";
import toast from "react-hot-toast";
import { Link } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import DashboardLayout from "../../components/layout/DashboardLayout";

import {
  getAllExpenses,
  createExpense,
  updateExpense,
  deleteExpense,
} from "../../services/expenseService";

export default function ExpensesPage() {
  const [expenses, setExpenses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [editingExpenseId, setEditingExpenseId] = useState(null);

  const [form, setForm] = useState({
    title: "",
    description: "",
    amount: "",
    expenseDate: "",
    category: "",
  });

  const loadExpenses = async () => {
    try {
      setLoading(true);

      const data = await getAllExpenses();

      setExpenses(data || []);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load expenses");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadExpenses();
  }, []);

  const resetForm = () => {
    setForm({
      title: "",
      description: "",
      amount: "",
      expenseDate: "",
      category: "",
    });

    setEditingExpenseId(null);
  };

  const handleEdit = (expense) => {
    setEditingExpenseId(expense.id);

    setForm({
      title: expense.title || "",
      description: expense.description || "",
      amount: expense.amount?.toString() || "",
      expenseDate: expense.expenseDate || "",
      category: expense.category || "",
    });

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setSaving(true);

      const payload = {
        ...form,
        amount: Number(form.amount),
      };

      if (editingExpenseId) {
        await updateExpense(editingExpenseId, payload);
        toast.success("Expense updated successfully 💰");
      } else {
        await createExpense(payload);
        toast.success("Expense added successfully 💰");
      }

      resetForm();
      await loadExpenses();
    } catch (error) {
      console.error(error);
      toast.error(
        error.response?.data?.message || "Failed to save expense"
      );
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this expense?"
    );

    if (!confirmed) return;

    try {
      await deleteExpense(id);

      await loadExpenses();

      toast.success("Expense deleted successfully 🗑️");
    } catch (error) {
      console.error(error);
      toast.error("Failed to delete expense");
    }
  };

  const totalThisMonth = expenses
    .filter((expense) => {
      const expenseDate = new Date(expense.expenseDate);
      const now = new Date();

      return (
        expenseDate.getMonth() === now.getMonth() &&
        expenseDate.getFullYear() === now.getFullYear()
      );
    })
    .reduce((sum, expense) => sum + Number(expense.amount || 0), 0);

  return (
    <DashboardLayout>
      <div className="space-y-8 px-2 pt-6 lg:px-4 lg:pt-8">
        {/* Header */}
        <div className="relative overflow-hidden rounded-[2rem] bg-gradient-to-r from-emerald-700 via-green-600 to-lime-500 p-8 text-white shadow-2xl shadow-emerald-600/20">

          <div className="absolute -right-10 -top-10 h-40 w-40 rounded-full bg-white/10"></div>

          <div className="absolute -bottom-16 right-24 h-56 w-56 rounded-full bg-white/5"></div>

          <div className="relative flex items-start gap-4">

            <Link
              to="/dashboard"
              className="flex h-12 w-12 items-center justify-center rounded-2xl bg-white/15 text-white backdrop-blur-md transition hover:bg-white/25"
            >
              <ArrowLeft size={22} />
            </Link>

            <div>
              <p className="text-sm font-semibold uppercase tracking-wide text-emerald-100">
                Expense Tracking
              </p>

              <h1 className="mt-2 text-4xl font-bold leading-tight lg:text-5xl">
                💰 Farm Expenses
              </h1>

              <p className="mt-3 max-w-2xl text-lg leading-relaxed text-emerald-50">
                Track seeds, fertilizer, irrigation, labour, and other farming expenses in one place.
              </p>
            </div>
          </div>
        </div>

        {/* Form */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-emerald-100 text-emerald-700">
                {editingExpenseId ? <Pencil size={24} /> : <Plus size={24} />}
              </div>

              <div>
                <h2 className="text-2xl font-bold text-emerald-700">
                  {editingExpenseId ? "✏️ Edit Expense" : "➕ Add Expense"}
                </h2>

                <p className="text-sm text-slate-500">
                  {editingExpenseId
                    ? "Update expense details."
                    : "Record a new farm expense."}
                </p>
              </div>
            </div>

            {editingExpenseId && (
              <button
                type="button"
                onClick={resetForm}
                className="inline-flex items-center gap-2 rounded-xl border border-slate-200 bg-white px-4 py-2 text-sm font-semibold text-slate-600 transition hover:bg-slate-50"
              >
                <X size={16} />
                Cancel
              </button>
            )}
          </div>

          <form
            onSubmit={handleSubmit}
            className="grid gap-4 md:grid-cols-2 lg:grid-cols-4"
          >
            <input
              placeholder="Expense title"
              value={form.title}
              onChange={(e) =>
                setForm({ ...form, title: e.target.value })
              }
              className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              required
            />

            <select
              value={form.category}
              onChange={(e) =>
                setForm({ ...form, category: e.target.value })
              }
              className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              required
            >
              <option value="">Select Category</option>
              <option value="SEED">🌱 Seed</option>
              <option value="FERTILIZER">🧪 Fertilizer</option>
              <option value="PESTICIDE">🐛 Pesticide</option>
              <option value="LABOUR">👷 Labour</option>
              <option value="MACHINERY">🚜 Machinery</option>
              <option value="IRRIGATION">💧 Irrigation</option>
              <option value="TRANSPORT">🚚 Transport</option>
              <option value="EQUIPMENT">🛠️ Equipment</option>
              <option value="OTHER">📌 Other</option>
            </select>

            <input
              type="number"
              step="0.01"
              min="0"
              placeholder="Amount"
              value={form.amount}
              onChange={(e) =>
                setForm({ ...form, amount: e.target.value })
              }
              className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              required
            />

            <input
              type="date"
              value={form.expenseDate}
              onChange={(e) =>
                setForm({
                  ...form,
                  expenseDate: e.target.value,
                })
              }
              className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              required
            />

            <div className="md:col-span-2 lg:col-span-4 flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Description
              </label>

              <textarea
                rows="3"
                placeholder="Write expense details..."
                value={form.description}
                onChange={(e) =>
                  setForm({
                    ...form,
                    description: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
              />
            </div>

            <div className="md:col-span-2 lg:col-span-4 flex justify-end pt-2">
              <button
                type="submit"
                disabled={saving}
                className="inline-flex items-center gap-2 rounded-2xl bg-emerald-600 px-6 py-3 font-semibold text-white shadow-md transition hover:bg-emerald-700 disabled:opacity-60"
              >
                <IndianRupee size={18} />
                {saving
                  ? editingExpenseId
                    ? "Updating..."
                    : "Saving..."
                  : editingExpenseId
                  ? "Update Expense"
                  : "Add Expense"}
              </button>
            </div>
          </form>
        </div>

        {/* Expense List */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center gap-3">
            <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-lime-100 text-lime-700">
              <CalendarDays size={24} />
            </div>

            <div>
              <h2 className="text-2xl font-bold text-slate-900">
                Recent Expenses
              </h2>

              <p className="text-sm text-slate-500">
                Your latest farm expense records
              </p>
            </div>
          </div>

          {loading ? (
            <div className="py-12 text-center text-slate-500">
              Loading expenses...
            </div>
          ) : expenses.length > 0 ? (
            <div className="space-y-4">
              {expenses.map((expense) => (
                <div
                  key={expense.id}
                  className="rounded-2xl border border-emerald-100 bg-emerald-50 p-5 transition hover:shadow-md"
                >
                  <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
                    <div>
                      <h3 className="text-lg font-bold text-emerald-700">
                        {expense.title}
                      </h3>

                      <p className="mt-1 text-sm font-semibold text-slate-700">
                        {expense.category}
                      </p>

                      {expense.description && (
                        <p className="mt-2 text-sm text-slate-600">
                          {expense.description}
                        </p>
                      )}
                    </div>

                    <div className="flex items-center gap-2">
                      <div className="rounded-xl bg-white px-4 py-2 text-right shadow-sm">
                        <p className="text-xs text-slate-500">
                          {expense.expenseDate}
                        </p>

                        <p className="text-lg font-bold text-emerald-700">
                          ₹{Number(expense.amount).toLocaleString()}
                        </p>
                      </div>

                      <button
                        onClick={() => handleEdit(expense)}
                        className="rounded-xl bg-white p-2 text-emerald-600 shadow-sm transition hover:bg-emerald-50"
                      >
                        <Pencil size={16} />
                      </button>

                      <button
                        onClick={() => handleDelete(expense.id)}
                        className="rounded-xl bg-white p-2 text-red-600 shadow-sm transition hover:bg-red-50"
                      >
                        <Trash2 size={16} />
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="rounded-2xl border border-dashed border-emerald-200 bg-white/70 p-12 text-center">
              <div className="text-5xl">💰</div>

              <h3 className="mt-4 text-xl font-semibold text-slate-800">
                No expenses added yet
              </h3>

              <p className="mt-2 text-slate-500">
                Start recording your farming expenses to track costs and
                profitability.
              </p>
            </div>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}
