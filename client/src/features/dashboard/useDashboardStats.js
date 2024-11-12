import { useQuery, useQueryClient } from "react-query";
import { getDahboardStats } from "./statsApi";

export function useDashboardStats() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["dashboardStats"],
    queryFn: () => getDahboardStats(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, data, error };
}
