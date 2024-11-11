import { useQuery, useQueryClient } from "react-query";
import { getProductsForCategories } from "./inventoryApi";

export function useProductsForCategory() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["categoriesStats"],
    queryFn: () => getProductsForCategories(jwtToken),
    enabled: !!jwtToken,
  });

  return { isLoading, data, error };
}
