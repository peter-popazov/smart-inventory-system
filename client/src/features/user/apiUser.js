import { baseUrl } from "../../constants/baseUrl";
import axios from "axios";

export async function updateUser(jwtToken, data) {
  const response = await axios.put(`${baseUrl}/user`, data, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
