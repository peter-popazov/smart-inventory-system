import { baseUrl } from "@/constants/baseUrl";
import axios from "axios";

export async function getAlerts(jwtToken) {
  const response = await axios.get(`${baseUrl}/alerts`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function processAlert(jwtToken, alertId) {
  const response = await axios.put(
    `${baseUrl}/alerts/${alertId}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    },
  );
  return response.data;
}
