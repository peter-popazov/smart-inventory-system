import { useQuery, useQueryClient } from "react-query";
import { getInventoryStock } from "./inventoryApi";

export function useInventoryStock() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["inventoryStock"],
    queryFn: () => getInventoryStock(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, data, error };
}
