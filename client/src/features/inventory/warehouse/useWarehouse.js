import { useQuery, useQueryClient } from "react-query";
import { getWarehouses } from "./apiWarehouse";

export function useWarehouse() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["warehouses"],
    queryFn: () => getWarehouses(jwtToken),
  });

  return { isLoading, data, error };
}
