import { baseUrl } from "../../constants/baseUrl";
import axios from "axios";

export async function register(email, password, role) {
  const response = await axios.post(`${baseUrl}/auth/register`, {
    email,
    password,
    registeringPerson: role,
  });
  return response.data;
}

export async function login(email, password) {
  const response = await axios.post(`${baseUrl}/auth/authenticate`, {
    email,
    password,
  });
  return response.data;
}
