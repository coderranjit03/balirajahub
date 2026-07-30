import { useState } from "react";
import { Eye, EyeOff, Leaf } from "lucide-react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

import { loginUser } from "../../services/authService";
import api from "../../api/axios";

export default function LoginPage() {

  const [showPassword, setShowPassword] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm();

  const navigate = useNavigate();

  const onSubmit = async (data) => {

    try {

      // Login API
      const response = await loginUser(data);

      console.log("FULL LOGIN RESPONSE:", response);

      const token = response?.data?.accessToken;

      if (!token) {

        toast.error("Token not received from server");

        return;
      }

      // Save JWT token
      localStorage.setItem("token", token);

      // Check farmer profile status
      const statusResponse = await api.get(
        "/api/farmer/profile/me/status"
      );

      const hasProfile = statusResponse?.data?.data;

      localStorage.setItem(
        "hasFarmerProfile",
        String(hasProfile)
      );

      toast.success("Login successful 🌾");

      // Redirect based on profile status
      if (hasProfile) {

        navigate("/dashboard");

      } else {

        toast("Please complete your farmer profile first 🌱");

        navigate("/profile/setup");
      }

    } catch (error) {

      console.error(error);

      toast.error(
        error.response?.data?.message || "Login failed"
      );
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-emerald-50 via-green-100 to-lime-50 flex items-center justify-center p-4">

      <div className="w-full max-w-md bg-white/80 backdrop-blur-xl rounded-3xl shadow-2xl border border-emerald-100 p-8">

        {/* Header */}
        <div className="text-center mb-8">

          <div className="mx-auto w-20 h-20 bg-gradient-to-br from-emerald-500 to-green-600 rounded-3xl flex items-center justify-center text-white shadow-lg mb-4">
            <Leaf size={36} />
          </div>

          <h1 className="text-3xl font-bold text-emerald-700">
            BalirajaHub
          </h1>

          <p className="text-slate-600 mt-2">
            Welcome back, Farmer 🌾
          </p>

        </div>

        {/* Form */}
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="space-y-5"
        >

          {/* Username */}
          <div>

            <label className="block text-sm font-semibold text-slate-700 mb-2">
              Email / Phone Number
            </label>

            <input
              type="text"
              placeholder="farmer@example.com or 890*******"
              className="w-full rounded-2xl border border-emerald-200 px-4 py-3 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition"
              {...register("username", {
                required: "Email or Phone Number is required",
              })}
            />

            {errors.username && (
              <p className="text-red-500 text-sm mt-1">
                {errors.username.message}
              </p>
            )}

          </div>

          {/* Password */}
          <div>

            <label className="block text-sm font-semibold text-slate-700 mb-2">
              Password
            </label>

            <div className="relative">

              <input
                type={showPassword ? "text" : "password"}
                placeholder="Enter your password"
                className="w-full rounded-2xl border border-emerald-200 px-4 py-3 pr-12 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition"
                {...register("password", {
                  required: "Password is required",
                })}
              />

              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-4 top-1/2 -translate-y-1/2 text-slate-500 hover:text-emerald-600"
              >
                {showPassword
                  ? <EyeOff size={20} />
                  : <Eye size={20} />}
              </button>

            </div>

            {errors.password && (
              <p className="text-red-500 text-sm mt-1">
                {errors.password.message}
              </p>
            )}

          </div>

          {/* Submit Button */}
          <button
            type="submit"
            disabled={isSubmitting}
            className="w-full bg-gradient-to-r from-emerald-600 to-green-600 text-white py-3 rounded-2xl font-semibold text-lg shadow-lg hover:from-emerald-700 hover:to-green-700 transition-all duration-300 hover:-translate-y-1 disabled:opacity-70 disabled:cursor-not-allowed"
          >
            {isSubmitting
              ? "Signing In..."
              : "Sign In 🌾"}
          </button>

        </form>

        {/* Footer */}
        <p className="text-center text-sm text-slate-600 mt-6">

          Don't have an account?{" "}

          <Link
            to="/register"
            className="text-emerald-600 font-semibold hover:underline"
          >
            Create one
          </Link>

        </p>

      </div>

    </div>
  );
}
