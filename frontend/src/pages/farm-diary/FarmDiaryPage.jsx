import { useEffect, useState } from "react";
import {
  BookOpen,
  CalendarDays,
  Plus,
  Pencil,
  Trash2,
  X,
} from "lucide-react";
import toast from "react-hot-toast";

import DashboardLayout from "../../components/layout/DashboardLayout";

import {
  getAllDiaryEntries,
  createDiaryEntry,
  updateDiaryEntry,
  deleteDiaryEntry,
} from "../../services/farmDiaryService";

export default function FarmDiaryPage() {
  const [entries, setEntries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [editingEntryId, setEditingEntryId] = useState(null);

  const [form, setForm] = useState({
    title: "",
    description: "",
    entryDate: "",
    activityType: "",
  });

  const loadData = async () => {
    try {
      setLoading(true);

      const diaryData = await getAllDiaryEntries();

      setEntries(diaryData || []);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load farm diary");
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
      entryDate: "",
      activityType: "",
    });

    setEditingEntryId(null);
  };

  const handleEdit = (entry) => {
    setEditingEntryId(entry.id);

    setForm({
      title: entry.title || "",
      description: entry.description || "",
      entryDate: entry.entryDate || "",
      activityType: entry.activityType || "",
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
        title: form.title,
        description: form.description,
        entryDate: form.entryDate,
        activityType: form.activityType,
      };

      if (editingEntryId) {
        await updateDiaryEntry(editingEntryId, payload);

        toast.success("Activity updated successfully 🌾");
      } else {
        await createDiaryEntry(payload);

        toast.success("Activity added successfully 🌾");
      }

      resetForm();

      await loadData();
    } catch (error) {
      console.error(error);

      toast.error(
        error.response?.data?.message ||
          "Failed to save activity"
      );
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this activity?"
    );

    if (!confirmed) return;

    try {
      await deleteDiaryEntry(id);

      await loadData();

      toast.success("Activity deleted successfully 🗑️");
    } catch (error) {
      console.error(error);
      toast.error("Failed to delete activity");
    }
  };

  return (
    <DashboardLayout>
      <div className="space-y-8 px-2 pt-6 lg:px-4 lg:pt-8">
        {/* Header */}
        <div className="rounded-[2rem] bg-gradient-to-r from-emerald-700 via-green-600 to-lime-500 p-8 text-white shadow-2xl">
          <div className="flex items-center gap-4">
            <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-white/15 backdrop-blur-md">
              <BookOpen size={28} />
            </div>

            <div>
              <h1 className="text-4xl font-bold">📒 Farm Diary</h1>

              <p className="mt-2 text-emerald-50">
                Record irrigation, fertilizer usage, pest observations, and
                daily farming activities.
              </p>
            </div>
          </div>
        </div>

        {/* Add/Edit Form */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-emerald-100 text-emerald-700">
                {editingEntryId ? <Pencil size={24} /> : <Plus size={24} />}
              </div>

              <div>
                <h2 className="text-2xl font-bold text-emerald-700">
                  {editingEntryId
                    ? "✏️ Edit Activity"
                    : "🌱 Add New Activity"}
                </h2>

                <p className="text-sm text-slate-500">
                  {editingEntryId
                    ? "Update your farm activity details."
                    : "Keep a daily record of important farm operations."}
                </p>
              </div>
            </div>

            {editingEntryId && (
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
            <div className="flex flex-col gap-2 md:col-span-2">
              <label className="text-sm font-semibold text-slate-700">
                Activity Title
              </label>

              <input
                placeholder="e.g. Drip irrigation for tomato field"
                value={form.title}
                onChange={(e) =>
                  setForm({
                    ...form,
                    title: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Activity Type */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Activity Type
              </label>

              <select
                value={form.activityType}
                onChange={(e) =>
                  setForm({
                    ...form,
                    activityType: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              >
                <option value="">Select Activity</option>
                <option value="SOWING">🌱 Sowing</option>
                <option value="IRRIGATION">💧 Irrigation</option>
                <option value="FERTILIZER">🧪 Fertilizer</option>
                <option value="PEST_CONTROL">🐛 Pest Control</option>
                <option value="HARVEST">🌾 Harvest</option>
                <option value="OTHER">📌 Other</option>
              </select>
            </div>

            {/* Date */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                📅 Entry Date
              </label>

              <input
                type="date"
                value={form.entryDate}
                onChange={(e) =>
                  setForm({
                    ...form,
                    entryDate: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Description */}
            <div className="flex flex-col gap-2 md:col-span-2 lg:col-span-4">
              <label className="text-sm font-semibold text-slate-700">
                Description
              </label>

              <textarea
                rows="4"
                placeholder="Write complete details about the activity, observations, fertilizer quantity, irrigation duration, etc."
                value={form.description}
                onChange={(e) =>
                  setForm({
                    ...form,
                    description: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Submit */}
            <div className="flex justify-end md:col-span-2 lg:col-span-4">
              <button
                type="submit"
                disabled={saving}
                className="inline-flex items-center gap-2 rounded-2xl bg-emerald-600 px-6 py-3 font-semibold text-white shadow-md transition hover:bg-emerald-700 disabled:opacity-60"
              >
                {saving
                  ? editingEntryId
                    ? "Updating..."
                    : "Saving..."
                  : editingEntryId
                  ? "Update Activity 🌾"
                  : "Add Activity 🌾"}
              </button>
            </div>
          </form>
        </div>

        {/* Activity List */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center gap-3">
            <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-lime-100 text-lime-700">
              <CalendarDays size={24} />
            </div>

            <div>
              <h2 className="text-2xl font-bold text-slate-900">
                Recent Activities
              </h2>

              <p className="text-sm text-slate-500">
                Your latest farm diary entries
              </p>
            </div>
          </div>

          {loading ? (
            <div className="py-12 text-center text-slate-500">
              Loading diary entries...
            </div>
          ) : entries.length > 0 ? (
            <div className="space-y-4">
              {entries.map((entry) => (
                <div
                  key={entry.id}
                  className="rounded-2xl border border-emerald-100 bg-emerald-50 p-5 transition hover:shadow-md"
                >
                  <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
                    <div>
                      <h3 className="text-lg font-bold text-emerald-700">
                        {entry.title}
                      </h3>

                      <p className="mt-1 text-sm font-semibold text-slate-700">
                        {entry.activityType}
                      </p>

                      {entry.description && (
                        <p className="mt-2 text-sm text-slate-600">
                          {entry.description}
                        </p>
                      )}
                    </div>

                    <div className="flex items-center gap-2">
                      <div className="rounded-xl bg-white px-3 py-2 text-sm font-medium text-slate-600 shadow-sm">
                        📅 {entry.entryDate}
                      </div>

                      <button
                        onClick={() => handleEdit(entry)}
                        className="rounded-xl bg-white p-2 text-emerald-600 shadow-sm transition hover:bg-emerald-50"
                      >
                        <Pencil size={16} />
                      </button>

                      <button
                        onClick={() => handleDelete(entry.id)}
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
              <div className="text-5xl">📒</div>

              <h3 className="mt-4 text-xl font-semibold text-slate-800">
                No diary entries yet
              </h3>

              <p className="mt-2 text-slate-500">
                Add your first farm activity to start maintaining a digital
                farming diary.
              </p>
            </div>
          )}
        </div>
      </div>
    </DashboardLayout>
  );
}
