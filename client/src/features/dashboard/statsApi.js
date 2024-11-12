import { baseUrl } from "../../constants/baseUrl";
import axios from "axios";

export async function getDahboardStats(jwtToken) {
  const response = await axios.get(`${baseUrl}/stats`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}