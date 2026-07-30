import { useEffect, useState } from "react";
import DashboardLayout from "../../components/layout/DashboardLayout";
import {
  getFarmerProfile,
  uploadProfileImage,
  updateFarmerProfile,
} from "../../services/farmerProfileService";
import {
  Phone,
  MapPin,
  LandPlot,
  Mail,
  Pencil,
  Save,
} from "lucide-react";
import toast from "react-hot-toast";

export default function ProfilePage() {
  const [profile, setProfile] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [editing, setEditing] = useState(false);

  const [form, setForm] = useState({
    village: "",
    taluka: "",
    district: "",
    state: "",
    latitude: "",
    longitude: "",
    pinCode: "",
    farmSize: "",
  });

  // Load profile
  useEffect(() => {
    const loadProfile = async () => {
      try {
        const data = await getFarmerProfile();

        setProfile(data);

        setForm({
          village: data.village || "",
          taluka: data.taluka || "",
          district: data.district || "",
          state: data.state || "",
          latitude: data.latitude || "",
          longitude: data.longitude || "",
          pinCode: data.pinCode || "",
          farmSize: data.farmSize || "",
        });
      } catch (error) {
        console.error(error);
        toast.error("Failed to load profile");
      }
    };

    loadProfile();
  }, []);

  // Upload image
  const handleImageUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setUploading(true);

    try {
      const updatedProfile = await uploadProfileImage(file);

      setProfile(updatedProfile);

      toast.success("Profile image updated successfully 🌾");
    } catch (error) {
      console.error(error);
      toast.error("Failed to upload image");
    } finally {
      setUploading(false);
    }
  };

  // Save profile
  const handleSaveProfile = async () => {
    try {
      const payload = {
        village: form.village,
        taluka: form.taluka,
        district: form.district,
        state: form.state,
        latitude: Number(form.latitude),
        longitude: Number(form.longitude),
        pinCode: form.pinCode,
        farmSize: Number(form.farmSize),
      };

      const updated = await updateFarmerProfile(payload);

      setProfile(updated);
      setEditing(false);

      toast.success("Profile updated successfully 🌾");
    } catch (error) {
      console.error(error);
      toast.error("Failed to update profile");
    }
  };

  if (!profile) {
    return (
      <DashboardLayout>
        <div className="flex h-64 items-center justify-center">
          <div className="h-12 w-12 animate-spin rounded-full border-4 border-emerald-200 border-t-emerald-600"></div>
        </div>
      </DashboardLayout>
    );
  }

  return (
    <DashboardLayout>
      <div className="mx-auto max-w-4xl space-y-6 px-2 pt-6 lg:px-4 lg:pt-8">
        {/* Header */}
        <div className="rounded-[2rem] bg-gradient-to-r from-emerald-700 via-green-600 to-lime-500 p-8 text-white shadow-2xl">
          <div className="flex flex-col items-center gap-5 text-center md:flex-row md:text-left">
            {/* Profile Image */}
            <div className="flex flex-col items-center gap-3">
              <div className="group relative h-28 w-28 overflow-hidden rounded-full border-4 border-white/80 bg-white/20 shadow-lg">
                {profile.profileImage ? (
                  <img
                    src={`http://localhost:8080${profile.profileImage}`}
                    alt="Farmer Profile"
                    className="h-full w-full object-cover"
                  />
                ) : (
                  <div className="flex h-full w-full items-center justify-center text-5xl">
                    👨‍🌾
                  </div>
                )}

                {editing && (
                  <label className="absolute inset-0 flex cursor-pointer items-center justify-center bg-black/40 text-2xl text-white opacity-0 transition group-hover:opacity-100">
                    📷
                    <input
                      type="file"
                      accept="image/png,image/jpeg"
                      onChange={handleImageUpload}
                      className="hidden"
                      disabled={uploading}
                    />
                  </label>
                )}
              </div>

              {editing && uploading && (
                <p className="text-sm text-emerald-50">
                  Uploading photo...
                </p>
              )}
            </div>

            {/* User Info */}
            <div className="flex-1">
              <p className="text-sm font-semibold uppercase tracking-wide text-emerald-100">
                Farmer Profile
              </p>

              <h1 className="mt-2 text-3xl font-bold lg:text-4xl">
                {profile.firstName} {profile.lastName}
              </h1>

              <div className="mt-3 flex flex-wrap items-center justify-center gap-4 text-sm text-emerald-50 md:justify-start">
                <span className="inline-flex items-center gap-2">
                  <Mail size={16} />
                  {profile.email}
                </span>

                <span className="inline-flex items-center gap-2">
                  <Phone size={16} />
                  {profile.phone}
                </span>
              </div>
            </div>

            {/* Edit Button */}
            <button
              onClick={() =>
                editing ? handleSaveProfile() : setEditing(true)
              }
              className="inline-flex items-center gap-2 rounded-2xl bg-white/15 px-5 py-3 font-semibold text-white backdrop-blur-md transition hover:bg-white/25"
            >
              {editing ? (
                <>
                  <Save size={18} />
                  Save Changes
                </>
              ) : (
                <>
                  <Pencil size={18} />
                  Edit Profile
                </>
              )}
            </button>
          </div>
        </div>

        {/* Contact + Farm */}
        <div className="grid gap-6 md:grid-cols-2">
          <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
            <div className="mb-4 flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-emerald-100 text-emerald-700">
                <Phone size={22} />
              </div>

              <div>
                <h2 className="text-lg font-bold text-slate-900">
                  Contact Information
                </h2>
                <p className="text-sm text-slate-500">
                  Your registered contact details
                </p>
              </div>
            </div>

            <div className="space-y-4 text-sm">
              <div className="flex items-start justify-between gap-4 rounded-2xl bg-slate-50 p-4">
                <span className="font-medium text-slate-600">Email</span>
                <span className="break-all text-right font-semibold text-slate-800">
                  {profile.email}
                </span>
              </div>

              <div className="flex items-start justify-between gap-4 rounded-2xl bg-slate-50 p-4">
                <span className="font-medium text-slate-600">Phone</span>
                <span className="text-right font-semibold text-slate-800">
                  {profile.phone}
                </span>
              </div>
            </div>
          </div>

          <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
            <div className="mb-4 flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-green-100 text-green-700">
                <LandPlot size={22} />
              </div>

              <div>
                <h2 className="text-lg font-bold text-slate-900">
                  Farm Details
                </h2>
                <p className="text-sm text-slate-500">
                  Information about your agricultural land
                </p>
              </div>
            </div>

            <div className="space-y-4 text-sm">
              <div className="flex items-start justify-between gap-4 rounded-2xl bg-slate-50 p-4">
                <span className="font-medium text-slate-600">Farm Size</span>

                {editing ? (
                  <input
                    type="number"
                    step="0.1"
                    value={form.farmSize}
                    onChange={(e) =>
                      setForm({
                        ...form,
                        farmSize: e.target.value,
                      })
                    }
                    className="w-28 rounded-lg border border-slate-300 px-2 py-1 text-right focus:border-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-200"
                  />
                ) : (
                  <span className="text-right font-semibold text-slate-800">
                    {profile.farmSize} Acres
                  </span>
                )}
              </div>

              <div className="flex items-start justify-between gap-4 rounded-2xl bg-slate-50 p-4">
                <span className="font-medium text-slate-600">Pin Code</span>

                {editing ? (
                  <input
                    value={form.pinCode}
                    onChange={(e) =>
                      setForm({
                        ...form,
                        pinCode: e.target.value,
                      })
                    }
                    className="w-32 rounded-lg border border-slate-300 px-2 py-1 text-right focus:border-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-200"
                  />
                ) : (
                  <span className="text-right font-semibold text-slate-800">
                    {profile.pinCode}
                  </span>
                )}
              </div>
            </div>
          </div>
        </div>

        {/* Location */}
        <div className="rounded-[2rem] border border-white/40 bg-white/80 p-6 shadow-xl backdrop-blur-md">
          <div className="mb-4 flex items-center gap-3">
            <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-lime-100 text-lime-700">
              <MapPin size={22} />
            </div>

            <div>
              <h2 className="text-lg font-bold text-slate-900">
                Farm Location
              </h2>
              <p className="text-sm text-slate-500">
                Your registered village and district information
              </p>
            </div>
          </div>

          <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
            {[
              { label: "Village", key: "village" },
              { label: "Taluka", key: "taluka" },
              { label: "District", key: "district" },
              { label: "State", key: "state" },
            ].map((item) => (
              <div key={item.key} className="rounded-2xl bg-slate-50 p-4">
                <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                  {item.label}
                </p>

                {editing ? (
                  <input
                    value={form[item.key]}
                    onChange={(e) =>
                      setForm({
                        ...form,
                        [item.key]: e.target.value,
                      })
                    }
                    className="mt-2 w-full rounded-lg border border-slate-300 px-3 py-2 focus:border-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-200"
                  />
                ) : (
                  <p className="mt-2 font-semibold text-slate-800">
                    {profile[item.key]}
                  </p>
                )}
              </div>
            ))}
          </div>

          <div className="mt-4 grid gap-4 md:grid-cols-2">
            <div className="rounded-2xl bg-slate-50 p-4">
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                Latitude
              </p>

              {editing ? (
                <input
                  type="number"
                  step="0.000001"
                  value={form.latitude}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      latitude: e.target.value,
                    })
                  }
                  className="mt-2 w-full rounded-lg border border-slate-300 px-3 py-2 focus:border-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-200"
                />
              ) : (
                <p className="mt-2 font-semibold text-slate-800">
                  {profile.latitude}
                </p>
              )}
            </div>

            <div className="rounded-2xl bg-slate-50 p-4">
              <p className="text-xs font-semibold uppercase tracking-wide text-slate-500">
                Longitude
              </p>

              {editing ? (
                <input
                  type="number"
                  step="0.000001"
                  value={form.longitude}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      longitude: e.target.value,
                    })
                  }
                  className="mt-2 w-full rounded-lg border border-slate-300 px-3 py-2 focus:border-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-200"
                />
              ) : (
                <p className="mt-2 font-semibold text-slate-800">
                  {profile.longitude}
                </p>
              )}
            </div>
          </div>
        </div>
      </div>
    </DashboardLayout>
  );
}