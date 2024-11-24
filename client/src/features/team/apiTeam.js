import { baseUrl } from "../../constants/baseUrl";
import axios from "axios";

export async function getTeams(jwtToken) {
  const response = await axios.get(`${baseUrl}/teams`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function getUserTeams(jwtToken) {
  const response = await axios.get(`${baseUrl}/teams/user`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function createTeam(jwtToken, data) {
  const response = await axios.post(`${baseUrl}/teams`, data, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function addTeam(jwtToken, data) {
  const response = await axios.post(`${baseUrl}/teams/invite`, data, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function removeTeamMember(jwtToken, data) {
  console.log(data);
  const response = await axios.post(`${baseUrl}/teams/remove`, data, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}

export async function deleteTeam(jwtToken, teamId) {
  const response = await axios.delete(`${baseUrl}/teams/${teamId}`, {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });
  return response.data;
}
