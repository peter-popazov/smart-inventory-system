import { baseUrl } from "../../constants/baseUrl";
import axios from "axios";

export async function updateUser(jwtToken, data) {
  console.log(jwtToken);
  console.log(data);

  const response = await axios.put(`${baseUrl}/user`, data, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
