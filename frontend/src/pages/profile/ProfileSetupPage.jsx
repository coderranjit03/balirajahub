import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/axios";
import toast from "react-hot-toast";

export default function ProfileSetupPage() {

  const navigate = useNavigate();

  const [form, setForm] = useState({
    village: "",
    taluka: "",
    district: "",
    state: "Maharashtra",
    latitude: "",
    longitude: "",
    pinCode: "",
    farmSize: "",
  });

  const [loading, setLoading] = useState(false);

  // Auto-fetch coordinates from village + district + state
  const fetchCoordinates = async () => {

    if (
      !form.village ||
      !form.district ||
      !form.state
    ) {
      return null;
    }

    try {

      const query =
        `${form.village}, ${form.district}, ${form.state}, India`;

      const url =
        `https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(query)}&format=json&limit=1`;

      const response = await fetch(url, {
        headers: {
          "Accept-Language": "en",
        },
      });

      const data = await response.json();

      if (data.length > 0) {

        return {
          latitude: parseFloat(data[0].lat),
          longitude: parseFloat(data[0].lon),
        };
      }

      return null;

    } catch (error) {

      console.error("Geocoding failed:", error);

      return null;
    }
  };

  // Optional: Use current device location
  const useCurrentLocation = () => {

    if (!navigator.geolocation) {

      toast.error("Geolocation is not supported");

      return;
    }

    navigator.geolocation.getCurrentPosition(
      (position) => {

        setForm({
          ...form,
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });

        toast.success("Location detected successfully 📍");
      },
      () => {
        toast.error("Unable to fetch current location");
      }
    );
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    setLoading(true);

    try {

      // Auto-detect coordinates if not already set
      let latitude = form.latitude;
      let longitude = form.longitude;

      if (!latitude || !longitude) {

        const coords = await fetchCoordinates();

        if (!coords) {

          toast.error(
            "Unable to find your village location. Please use current location."
          );

          setLoading(false);

          return;
        }

        latitude = coords.latitude;
        longitude = coords.longitude;
      }

      await api.post(
        "/api/farmer/profile",
        {
          village: form.village,
          taluka: form.taluka,
          district: form.district,
          state: form.state,
          latitude,
          longitude,
          pinCode: form.pinCode,
          farmSize: Number(form.farmSize),
        }
      );

      localStorage.setItem(
        "hasFarmerProfile",
        "true"
      );

      toast.success("Farmer profile created successfully 🌱");

      navigate("/dashboard");

    } catch (error) {

      console.error(error);

      toast.error(
        error.response?.data?.message ||
        "Failed to create farmer profile"
      );

    } finally {

      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-emerald-50 via-green-50 to-lime-50 flex items-center justify-center p-6">

      <div className="w-full max-w-3xl rounded-[2rem] border border-white/40 bg-white/80 p-8 shadow-2xl backdrop-blur-md">

        <div className="text-center mb-8">

          <div className="text-5xl mb-3">🌾</div>

          <h1 className="text-3xl font-bold text-emerald-700">
            Complete Farmer Profile
          </h1>

          <p className="text-slate-500 mt-2">
            Provide your farm location details to access Dashboard,
            Crops, AI Advisory, and personalized weather updates.
          </p>
        </div>

        <form
          onSubmit={handleSubmit}
          className="grid gap-4 md:grid-cols-2"
        >

          <input
            placeholder="Village"
            value={form.village}
            onChange={(e) =>
              setForm({ ...form, village: e.target.value })
            }
            className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            required
          />

          <input
            placeholder="Taluka"
            value={form.taluka}
            onChange={(e) =>
              setForm({ ...form, taluka: e.target.value })
            }
            className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            required
          />

          <input
            placeholder="District"
            value={form.district}
            onChange={(e) =>
              setForm({ ...form, district: e.target.value })
            }
            className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            required
          />

          <select
            value={form.state}
            onChange={(e) =>
              setForm({ ...form, state: e.target.value })
            }
            className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            required
          >
            <option value="Maharashtra">Maharashtra</option>
            <option value="Karnataka">Karnataka</option>
            <option value="Gujarat">Gujarat</option>
            <option value="Madhya Pradesh">Madhya Pradesh</option>
          </select>

          <input
            placeholder="Pin Code"
            value={form.pinCode}
            onChange={(e) =>
              setForm({ ...form, pinCode: e.target.value })
            }
            className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            required
          />

          <input
            type="number"
            step="0.1"
            placeholder="Farm Size (Acres)"
            value={form.farmSize}
            onChange={(e) =>
              setForm({ ...form, farmSize: e.target.value })
            }
            className="rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            required
          />

          {/* Current Location Button */}
          <button
            type="button"
            onClick={useCurrentLocation}
            className="md:col-span-2 rounded-2xl border border-blue-200 bg-blue-50 px-6 py-3 font-semibold text-blue-700 transition hover:bg-blue-100"
          >
            📍 Use Current Location
          </button>

          {/* Save Button */}
          <button
            type="submit"
            disabled={loading}
            className="md:col-span-2 rounded-2xl bg-emerald-600 px-6 py-3 font-semibold text-white transition hover:bg-emerald-700 disabled:opacity-50 shadow-lg"
          >
            {loading
              ? "Saving Profile..."
              : "Save & Continue 🌱"}
          </button>

        </form>
      </div>
    </div>
  );
}
