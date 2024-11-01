import { useMutation, useQueryClient } from "react-query";
import { createProduct as createProductFn } from "./apiInventory";
import toast from "react-hot-toast";

export function useCreateProduct() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: createProduct, isLoading: isCreateing } = useMutation({
    mutationFn: (product) => createProductFn(jwtToken, product),
    onSuccess: () => {
      toast.success("Product added");
      queryClient.invalidateQueries({ queryKey: ["inventory"] });
    },
    onError: () => {
      toast.error("Error creating product");
    },
  });

  return { createProduct, isCreateing };
}
