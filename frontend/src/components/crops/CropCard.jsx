import {
  CalendarDays,
  LandPlot,
  Trash2,
  Pencil,
} from "lucide-react";

export default function CropCard({
  crop,
  onDelete,
  onEdit,
}) {
  const seasonColors = {
    KHARIF: "bg-emerald-100 text-emerald-700",
    RABI: "bg-blue-100 text-blue-700",
    SUMMER: "bg-amber-100 text-amber-700",
  };

    const stageColors = {
        SOWING: "bg-yellow-100 text-yellow-700",
        GROWING: "bg-emerald-100 text-emerald-700",
        FLOWERING: "bg-pink-100 text-pink-700",
        HARVEST_READY: "bg-lime-100 text-lime-700",
      };

  return (
    <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md transition-all duration-300 hover:-translate-y-1 hover:shadow-2xl">

            {/* Header */}
            <div className="mb-5 flex items-start justify-between gap-3">
              <div>
                <h3 className="text-2xl font-bold text-slate-900">
                  🌾 {crop.cropName}
                </h3>

                <div className="mt-2 flex flex-wrap gap-2">
                  <span
                    className={`rounded-full px-3 py-1 text-xs font-semibold ${
                      seasonColors[crop.season] ||
                      "bg-slate-100 text-slate-700"
                    }`}
                  >
                    {crop.season}
                  </span>

                  {crop.growthStage && (
                    <span
                      className={`rounded-full px-3 py-1 text-xs font-semibold ${
                        stageColors[crop.growthStage] ||
                        "bg-slate-100 text-slate-700"
                      }`}
                    >
                      {crop.growthStage.replace("_", " ")}
                    </span>
                  )}
                </div>
              </div>
            </div>

      {/* Details */}
      <div className="space-y-3 text-sm text-slate-600">

        <div className="flex items-center gap-3 rounded-2xl bg-slate-50 px-4 py-3">
          <LandPlot size={18} className="text-emerald-600" />

          <div className="flex flex-col">
            <span className="text-xs font-medium uppercase tracking-wide text-slate-400">
              Area
            </span>

            <span className="font-semibold text-slate-800">
              {crop.area} Acres
            </span>
          </div>
        </div>

        <div className="flex items-center gap-3 rounded-2xl bg-slate-50 px-4 py-3">
          <CalendarDays size={18} className="text-emerald-600" />

          <div className="flex flex-col">
            <span className="text-xs font-medium uppercase tracking-wide text-slate-400">
              Sowing Date
            </span>

            <span className="font-semibold text-slate-800">
              {crop.sowingDate}
            </span>
          </div>
        </div>

        <div className="flex items-center gap-3 rounded-2xl bg-slate-50 px-4 py-3">
          <CalendarDays size={18} className="text-lime-600" />

          <div className="flex flex-col">
            <span className="text-xs font-medium uppercase tracking-wide text-slate-400">
              Expected Harvest
            </span>

            <span className="font-semibold text-slate-800">
              {crop.expectedHarvestDate}
            </span>
          </div>
        </div>

      </div>

      {/* Actions */}
      <div className="mt-6 flex gap-3">

        <button
          onClick={() => onEdit(crop)}
          className="flex flex-1 items-center justify-center gap-2 rounded-2xl bg-emerald-600 px-4 py-3 text-sm font-semibold text-white transition hover:bg-emerald-700 hover:shadow-lg hover:shadow-emerald-600/20"
        >
          <Pencil size={16} />
          Edit
        </button>

        <button
          onClick={() => onDelete(crop.id)}
          className="flex items-center justify-center gap-2 rounded-2xl bg-red-50 px-4 py-3 text-sm font-semibold text-red-600 transition hover:bg-red-100 hover:shadow-lg hover:shadow-red-200/40"
        >
          <Trash2 size={16} />
          Delete
        </button>

      </div>

    </div>
  );
}
