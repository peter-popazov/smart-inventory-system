import axios from "axios";
import { baseUrl } from "../../../constants/baseUrl";

export async function getWarehouses(jwtToken) {
  const response = await axios.get(`${baseUrl}/warehouses`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}