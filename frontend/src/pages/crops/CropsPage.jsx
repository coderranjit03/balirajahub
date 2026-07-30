import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  ArrowLeft,
  Sprout,
  Plus,
  X,
} from "lucide-react";
import toast from "react-hot-toast";

import DashboardLayout from "../../components/layout/DashboardLayout";
import CropCard from "../../components/crops/CropCard";

import {
  getAllCrops,
  createCrop,
  updateCrop,
  deleteCrop,
} from "../../services/cropService";

export default function CropsPage() {
  const [crops, setCrops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [editingCropId, setEditingCropId] = useState(null);

  const [form, setForm] = useState({
    cropName: "",
    season: "",
    area: "",
    sowingDate: "",
    expectedHarvestDate: "",
  });

  const loadCrops = async () => {
    try {
      setLoading(true);

      const data = await getAllCrops();

      setCrops(data || []);
    } catch (error) {
      console.error("Failed to load crops:", error);
      toast.error("Failed to load crops");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCrops();
  }, []);

  const resetForm = () => {
    setForm({
      cropName: "",
      season: "",
      area: "",
      sowingDate: "",
      expectedHarvestDate: "",
    });

    setEditingCropId(null);
  };

  const handleEdit = (crop) => {
    setEditingCropId(crop.id);

    setForm({
      cropName: crop.cropName || "",
      season: crop.season || "",
      area: crop.area ? crop.area.toString() : "",
      sowingDate: crop.sowingDate || "",
      expectedHarvestDate: crop.expectedHarvestDate || "",
    });

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      ...form,
      area: Number(form.area),
    };

    try {
      setSaving(true);

      if (editingCropId) {
        await updateCrop(editingCropId, payload);
        toast.success("Crop updated successfully 🌾");
      } else {
        await createCrop(payload);
        toast.success("Crop added successfully 🌱");
      }

      resetForm();
      await loadCrops();
    } catch (error) {
      console.error("Failed to save crop:", error);

      toast.error(
        error.response?.data?.message ||
          "Failed to save crop"
      );
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this crop?"
    );

    if (!confirmed) return;

    try {
      await deleteCrop(id);
      await loadCrops();

      toast.success("Crop deleted successfully 🗑️");
    } catch (error) {
      console.error("Failed to delete crop:", error);

      toast.error(
        error.response?.data?.message ||
          "Failed to delete crop"
      );
    }
  };

  return (
    <DashboardLayout>
      <div className="space-y-8 px-1 pt-6 sm:px-2 lg:px-4 lg:pt-8">
        {/* Header */}
        <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
          <div className="flex items-center gap-4">
            <Link
              to="/dashboard"
              className="flex h-12 w-12 items-center justify-center rounded-2xl border border-emerald-200 bg-white text-emerald-700 shadow-sm transition hover:bg-emerald-50"
            >
              <ArrowLeft size={22} />
            </Link>

            <div>
              <h1 className="text-4xl font-bold text-emerald-700">
                🌱 My Crops
              </h1>

              <p className="mt-2 text-slate-600">
                Manage all your crop activities, sowing dates, and harvest planning in one place.
              </p>
            </div>
          </div>

          <div className="rounded-2xl bg-white/80 px-4 py-3 text-sm font-medium text-emerald-700 shadow-md backdrop-blur-md">
            Total Crops: <span className="font-bold">{crops.length}</span>
          </div>
        </div>

        {/* Add/Edit Crop Form */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-6 flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-emerald-100 text-emerald-700">
                {editingCropId ? <Sprout size={24} /> : <Plus size={24} />}
              </div>

              <div>
                <h2 className="text-2xl font-bold text-emerald-700">
                  {editingCropId ? "✏️ Edit Crop" : "🌱 Add New Crop"}
                </h2>

                <p className="text-sm text-slate-500">
                  {editingCropId
                    ? "Update crop details, area, and harvest planning."
                    : "Enter crop details to track sowing, area, and expected harvest."}
                </p>
              </div>
            </div>

            {editingCropId && (
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
            className="grid gap-4 md:grid-cols-2 lg:grid-cols-5"
          >
            {/* Crop Name */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Crop Name
              </label>

              <input
                placeholder="e.g. WaterMelon"
                value={form.cropName}
                onChange={(e) =>
                  setForm({
                    ...form,
                    cropName: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Season */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Season
              </label>

              <select
                value={form.season}
                onChange={(e) =>
                  setForm({
                    ...form,
                    season: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              >
                <option value="">Select Season</option>
                <option value="KHARIF">Kharif</option>
                <option value="RABI">Rabi</option>
                <option value="SUMMER">Summer</option>
              </select>
            </div>

            {/* Area */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                Area (Acres)
              </label>

              <input
                type="number"
                step="0.1"
                min="0.1"
                placeholder="e.g. 5"
                value={form.area}
                onChange={(e) =>
                  setForm({
                    ...form,
                    area: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Sowing Date */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                🌱 Sowing Date
              </label>

              <input
                type="date"
                value={form.sowingDate}
                onChange={(e) =>
                  setForm({
                    ...form,
                    sowingDate: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Harvest Date */}
            <div className="flex flex-col gap-2">
              <label className="text-sm font-semibold text-slate-700">
                🌾 Expected Harvest
              </label>

              <input
                type="date"
                value={form.expectedHarvestDate}
                onChange={(e) =>
                  setForm({
                    ...form,
                    expectedHarvestDate: e.target.value,
                  })
                }
                className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                required
              />
            </div>

            {/* Submit */}
            <div className="flex justify-end pt-2 md:col-span-2 lg:col-span-5">
              <button
                type="submit"
                disabled={saving}
                className="inline-flex items-center gap-2 rounded-2xl bg-emerald-600 px-6 py-3 font-semibold text-white shadow-md transition hover:bg-emerald-700 hover:shadow-emerald-600/30 disabled:opacity-60"
              >
                <Sprout size={18} />

                {saving
                  ? editingCropId
                    ? "Updating..."
                    : "Saving..."
                  : editingCropId
                  ? "Update Crop"
                  : "Add Crop"}
              </button>
            </div>
          </form>
        </div>

        {/* Crop List */}
        {loading ? (
          <div className="rounded-[2rem] border border-white/40 bg-white/80 p-12 text-center shadow-xl backdrop-blur-md">
            <div className="animate-pulse text-5xl">🌱</div>

            <p className="mt-4 text-slate-600">
              Loading your crops...
            </p>
          </div>
        ) : (
          <div className="grid gap-6 md:grid-cols-2 xl:grid-cols-3">
            {crops.length > 0 ? (
              crops.map((crop) => (
                <CropCard
                  key={crop.id}
                  crop={crop}
                  onDelete={handleDelete}
                  onEdit={handleEdit}
                />
              ))
            ) : (
              <div className="col-span-full rounded-[2rem] border border-dashed border-emerald-200 bg-white/70 p-12 text-center shadow-sm backdrop-blur-md">
                <div className="text-5xl">🌱</div>

                <h3 className="mt-4 text-xl font-semibold text-slate-800">
                  No crops added yet
                </h3>

                <p className="mt-2 text-slate-500">
                  Start by adding your first crop and track its farming journey.
                </p>
              </div>
            )}
          </div>
        )}
      </div>
    </DashboardLayout>
  );
}
