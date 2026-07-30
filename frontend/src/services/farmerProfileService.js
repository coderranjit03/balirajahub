import api from "../api/axios";

// Get profile
export const getFarmerProfile = async () => {
  const response = await api.get("/api/farmer/profile");
  return response.data.data;
};

// Update profile
export const updateFarmerProfile = async (profileData) => {
  const response = await api.put(
    "/api/farmer/profile",
    profileData
  );

  return response.data.data;
};

// Upload image
export const uploadProfileImage = async (file) => {
  const formData = new FormData();

  formData.append("image", file);

  const response = await api.post(
    "/api/farmer/profile/image",
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }
  );

  return response.data.data;
};
