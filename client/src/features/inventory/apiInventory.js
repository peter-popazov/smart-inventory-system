import axios from "axios";
import { baseUrl } from "../../constants/baseUrl";

export async function getInventory(jwtToken) {
  const response = await axios.get(`${baseUrl}/products`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function createProduct(jwtToken, data) {
  const response = await axios.post(
    `${baseUrl}/products`,
    {
      ...data,
    },
    {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    },
  );
  return response.data;
}
export async function editProduct(jwtToken, data) {
  const response = await axios.put(
    `${baseUrl}/products`,
    {
      ...data,
    },
    {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    },
  );
  return response.data;
}

export async function deleteProduct(jwtToken, id) {
  const response = await axios.delete(`${baseUrl}/products/${id}`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
