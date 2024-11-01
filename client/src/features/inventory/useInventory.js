import { useQuery, useQueryClient } from "react-query";
import { getInventory } from "./apiInventory";

export function useInventory() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const {
    isLoading,
    data: inventory,
    error,
  } = useQuery({
    queryKey: ["inventory"],
    queryFn: () => getInventory(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, inventory, error };
}
