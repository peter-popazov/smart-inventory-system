import { useQuery, useQueryClient } from "react-query";
import { getTeams } from "./apiTeam";

export function useTeams() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["teams"],
    queryFn: () => getTeams(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, data, error };
}
