import { baseUrl } from "@/constants/baseUrl";
import axios from "axios";

export async function getRefilmentEmail(jwtToken, productId) {
  const response = await axios.get(`${baseUrl}/products/refill/${productId}`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
