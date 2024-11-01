import axios from "axios";
import { baseUrl } from "../../../constants/baseUrl";

export async function getCategories(jwtToken) {
  const response = await axios.get(`${baseUrl}/categories`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function createCategory(jwtToken, category) {
  const response = await axios.post(
    `${baseUrl}/categories`,
    {
      name: category,
    },
    {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    },
  );
  return response.data;
}
