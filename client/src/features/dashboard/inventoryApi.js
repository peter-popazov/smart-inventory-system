import { baseUrl } from "../../constants/baseUrl";
import axios from "axios";

export async function getInventoryStock(jwtToken) {
  const response = await axios.get(`${baseUrl}/products/movements/all`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function getProductsForCategories(jwtToken) {
  const response = await axios.get(`${baseUrl}/categories/stats`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function getInventoryMovements(jwtToken) {
  const response = await axios.get(`${baseUrl}/products/movements`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
