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

export async function createWarehouse(jwtToken, data) {
  const response = await axios.post(`${baseUrl}/warehouses`, data, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
