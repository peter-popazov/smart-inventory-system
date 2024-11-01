import { useQuery, useQueryClient } from "react-query";
import { getCategories } from "./apiCategory";

export function useCategory() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { isLoading, data, error } = useQuery({
    queryKey: ["categories"],
    queryFn: () => getCategories(jwtToken),
  });

  return { isLoading, data, error };
}
