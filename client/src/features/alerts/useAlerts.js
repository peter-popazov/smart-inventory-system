import { useQuery, useQueryClient } from "react-query";
import { getAlerts } from "./apiAlerts";

export function useAlerts() {
    const queryClient = useQueryClient();
    const user = queryClient.getQueryData("user");
    const jwtToken = user?.jwt_token;
  
    const {
      isLoading,
      data,
      error,
    } = useQuery({
      queryKey: ["alerts"],
      queryFn: () => getAlerts(jwtToken),
      enabled: !!jwtToken,
    });
  
    return { isLoading, data, error };
  }
  