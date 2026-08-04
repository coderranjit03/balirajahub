import api from "../api/axios";

const BASE_URL = "/api/expenses";

export const getAllExpenses = async () => {
  const response = await api.get(BASE_URL);
  return response.data.data;
};

export const createExpense = async (expenseData) => {
  const response = await api.post(BASE_URL, expenseData);
  return response.data.data;
};

export const updateExpense = async (id, expenseData) => {
  const response = await api.put(`${BASE_URL}/${id}`, expenseData);
  return response.data.data;
};

export const deleteExpense = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};