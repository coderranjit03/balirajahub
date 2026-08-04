import api from "../api/axios";

const BASE_URL = "/api/reminders";

// Get all reminders
export const getAllReminders = async () => {
  const response = await api.get(BASE_URL);
  return response.data.data;
};

// Create reminder
export const createReminder = async (reminderData) => {
  const response = await api.post(BASE_URL, reminderData);
  return response.data.data;
};

// Get reminder by ID
export const getReminderById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);
  return response.data.data;
};

// Update reminder
export const updateReminder = async (id, reminderData) => {
  const response = await api.put(`${BASE_URL}/${id}`, reminderData);
  return response.data.data;
};

// Delete reminder
export const deleteReminder = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};

// Mark reminder as completed
export const markReminderCompleted = async (id) => {
  const response = await api.patch(`${BASE_URL}/${id}/complete`);
  return response.data.data;
};

// Upcoming Reminders
export const getUpcomingReminders = async () => {
  const reminders = await getAllReminders();

  return reminders
    .filter((r) => r.status === "PENDING")
    .sort((a, b) =>
      new Date(a.reminderDate) - new Date(b.reminderDate)
    )
    .slice(0, 3);
};