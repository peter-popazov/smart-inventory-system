import { useQuery, useQueryClient } from "react-query";
import { getInventoryMovements } from "./inventoryApi";

export function useInventoryMovements() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["inventoryMovements"],
    queryFn: () => getInventoryMovements(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, data, error };
}
