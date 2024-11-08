import { useQuery, useQueryClient } from "react-query";
import { getUserTeams } from "./apiTeam";

export function useUserTeams() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["teams"],
    queryFn: () => getUserTeams(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, data, error };
}
