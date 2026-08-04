import { useEffect, useState } from "react";
import {
  Bell,
  CalendarDays,
  Plus,
  Pencil,
  Trash2,
  CheckCircle,
  X,
  ArrowLeft,
} from "lucide-react";
import { Link } from "react-router-dom";
import toast from "react-hot-toast";

import DashboardLayout from "../../components/layout/DashboardLayout";

import {
  getAllReminders,
  createReminder,
  updateReminder,
  deleteReminder,
  markReminderCompleted,
} from "../../services/reminderService";

import { getAllCrops } from "../../services/cropService";

export default function RemindersPage() {
  const [reminders, setReminders] = useState([]);
  const [crops, setCrops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [editingReminderId, setEditingReminderId] = useState(null);

  const [form, setForm] = useState({
    title: "",
    description: "",
    cropId: "",
    activityDate: "",
    reminderDate: "",
  });

  // Load reminders + crops
  const loadData = async () => {
    try {
      setLoading(true);

      const [cropData, reminderData] = await Promise.all([
        getAllCrops(),
        getAllReminders(),
      ]);

      setCrops(cropData || []);
      setReminders(reminderData || []);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load reminders");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const resetForm = () => {
    setForm({
      title: "",
      description: "",
      cropId: "",
      activityDate: "",
      reminderDate: "",
    });

    setEditingReminderId(null);
  };

  // Edit reminder
  const handleEdit = (reminder) => {
    setEditingReminderId(reminder.id);

    setForm({
      title: reminder.title || "",
      description: reminder.description || "",
      cropId: reminder.cropId ? reminder.cropId.toString() : "",
      activityDate: reminder.activityDate || "",
      reminderDate: reminder.reminderDate || "",
    });

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  // Save reminder
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setSaving(true);

      const payload = {
        title: form.title,
        description: form.description,
        activityDate: form.activityDate,
        reminderDate: form.reminderDate,
        cropId: form.cropId ? Number(form.cropId) : null,
      };

      if (editingReminderId) {
        await updateReminder(editingReminderId, payload);
        toast.success("Reminder updated successfully 🔔");
      } else {
        await createReminder(payload);
        toast.success("Reminder added successfully 🔔");
      }

      resetForm();
      await loadData();
    } catch (error) {
      console.error(error);

      toast.error(
        error.response?.data?.message ||
          "Failed to save reminder"
      );
    } finally {
      setSaving(false);
    }
  };

  // Delete reminder
  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this reminder?"
    );

    if (!confirmed) return;

    try {
      await deleteReminder(id);
      await loadData();

      toast.success("Reminder deleted successfully 🗑️");
    } catch (error) {
      console.error(error);
      toast.error("Failed to delete reminder");
    }
  };

  // Complete reminder
  const handleComplete = async (id) => {
    try {
      await markReminderCompleted(id);
      await loadData();

      toast.success("Reminder marked as completed ✅");
    } catch (error) {
      console.error(error);
      toast.error("Failed to complete reminder");
    }
  };

  return (
    <DashboardLayout>
      <div className="space-y-8 px-2 pt-6 lg:px-4 lg:pt-8">
        {/* Header */}
        <div className="relative overflow-hidden rounded-[2rem] bg-gradient-to-r from-amber-600 via-yellow-500 to-orange-400 p-8 text-white shadow-2xl shadow-amber-500/20">
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
              <p className="text-sm font-semibold uppercase tracking-wide text-yellow-100">
                Task Management
              </p>

              <h1 className="mt-2 text-4xl font-bold leading-tight lg:text-5xl">
                🔔 Farm Reminders
              </h1>

              <p className="mt-3 max-w-2xl text-lg leading-relaxed text-yellow-50">
                Never miss irrigation, fertilizer application, harvesting, or other important farm tasks.
              </p>
            </div>
          </div>
        </div>

        {/* Add/Edit Reminder Form */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-amber-100 text-amber-700">
                {editingReminderId ? (
                  <Pencil size={24} />
                ) : (
                  <Plus size={24} />
                )}
              </div>

              <div>
                <h2 className="text-2xl font-bold text-amber-700">
                  {editingReminderId
                    ? "✏️ Edit Reminder"
                    : "🔔 Add New Reminder"}
                </h2>

                <p className="text-sm text-slate-500">
                  {editingReminderId
                    ? "Update your reminder details."
                    : "Schedule important farm activities and maintenance tasks."}
                </p>
              </div>
            </div>

            {editingReminderId && (
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
            {/* Title */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Reminder Title
              </label>

              <input
                placeholder="e.g. Tractor Servicing"
                value={form.title}
                onChange={(e) =>
                  setForm({
                    ...form,
                    title: e.target.value,
                  })
                }
                className="rounded-2xl border border-amber-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-amber-500"
                required
              />
            </div>

            {/* Crop (Optional) */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Crop (Optional)
              </label>

              <select
                value={form.cropId}
                onChange={(e) =>
                  setForm({
                    ...form,
                    cropId: e.target.value,
                  })
                }
                className="rounded-2xl border border-amber-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-amber-500"
              >
                <option value="">
                  General Reminder
                </option>

                {crops.map((crop) => (
                  <option key={crop.id} value={crop.id}>
                    {crop.cropName}
                  </option>
                ))}
              </select>
            </div>

            {/* Reminder Date */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                🔔 Reminder Date
              </label>

              <input
                type="date"
                value={form.reminderDate}
                onChange={(e) =>
                  setForm({
                    ...form,
                    reminderDate: e.target.value,
                  })
                }
                className="rounded-2xl border border-amber-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-amber-500"
                required
              />
            </div>

            {/* Activity Date */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                📅 Activity Date
              </label>

              <input
                type="date"
                value={form.activityDate}
                onChange={(e) =>
                  setForm({
                    ...form,
                    activityDate: e.target.value,
                  })
                }
                className="rounded-2xl border border-amber-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-amber-500"
                required
              />
            </div>

            {/* Description */}
            <div className="flex flex-col gap-2 md:col-span-2 lg:col-span-4">
              <label className="text-sm font-semibold text-slate-700">
                Description
              </label>

              <textarea
                rows="3"
                placeholder="Write reminder details..."
                value={form.description}
                onChange={(e) =>
                  setForm({
                    ...form,
                    description: e.target.value,
                  })
                }
                className="rounded-2xl border border-amber-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-amber-500"
                required
              />
            </div>

            {/* Submit */}
            <div className="flex justify-end pt-2 md:col-span-2 lg:col-span-4">
              <button
                type="submit"
                disabled={saving}
                className="inline-flex items-center gap-2 rounded-2xl bg-amber-500 px-6 py-3 font-semibold text-white shadow-md transition hover:bg-amber-600 disabled:opacity-60"
              >
                <Bell size={18} />

                {saving
                  ? editingReminderId
                    ? "Updating..."
                    : "Saving..."
                  : editingReminderId
                  ? "Update Reminder"
                  : "Add Reminder"}
              </button>
            </div>
          </form>
        </div>

        {/* Reminder List */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center gap-3">
            <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-yellow-100 text-yellow-700">
              <CalendarDays size={24} />
            </div>

            <div>
              <h2 className="text-2xl font-bold text-slate-900">
                Upcoming Reminders
              </h2>

              <p className="text-sm text-slate-500">
                Your scheduled farm tasks and maintenance reminders
              </p>
            </div>
          </div>

          {loading ? (
            <div className="py-12 text-center text-slate-500">
              Loading reminders...
            </div>
          ) : reminders.length > 0 ? (
            <div className="space-y-4">
              {reminders.map((reminder) => (
                <div
                  key={reminder.id}
                  className="rounded-2xl border border-amber-100 bg-amber-50 p-5 transition hover:shadow-md"
                >
                  <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
                    <div>
                      <div className="flex items-center gap-2">
                        <h3 className="text-lg font-bold text-amber-700">
                          {reminder.title}
                        </h3>

                        {reminder.status === "COMPLETED" && (
                          <span className="rounded-full bg-emerald-100 px-2 py-1 text-xs font-semibold text-emerald-700">
                            Completed
                          </span>
                        )}
                      </div>

                      <p className="mt-1 text-sm font-semibold text-slate-700">
                        🌱 {reminder.cropName || "General Reminder"}
                      </p>

                      <p className="mt-2 text-sm text-slate-600">
                        {reminder.description}
                      </p>

                      <div className="mt-3 flex flex-wrap gap-3 text-xs text-slate-500">
                        <span>
                          🔔 Reminder: {reminder.reminderDate}
                        </span>

                        <span>
                          📅 Activity: {reminder.activityDate}
                        </span>
                      </div>
                    </div>

                    <div className="flex items-center gap-2">
                      {reminder.status !== "COMPLETED" && (
                        <button
                          onClick={() => handleComplete(reminder.id)}
                          className="rounded-xl bg-white p-2 text-emerald-600 shadow-sm transition hover:bg-emerald-50"
                        >
                          <CheckCircle size={16} />
                        </button>
                      )}

                      <button
                        onClick={() => handleEdit(reminder)}
                        className="rounded-xl bg-white p-2 text-amber-600 shadow-sm transition hover:bg-amber-50"
                      >
                        <Pencil size={16} />
                      </button>

                      <button
                        onClick={() => handleDelete(reminder.id)}
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
            <div className="rounded-2xl border border-dashed border-amber-200 bg-white/70 p-12 text-center">
              <div className="text-5xl">📅</div>

              <h3 className="mt-4 text-xl font-semibold text-slate-800">
                No reminders yet
              </h3>

              <p className="mt-2 text-slate-500">
                Add your first reminder to stay on top of important farming activities.
              </p>
            </div>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}
