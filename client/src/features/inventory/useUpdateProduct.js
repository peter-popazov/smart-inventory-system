import { useMutation, useQueryClient } from "react-query";
import { editProduct as editProductFn } from "./apiInventory";
import toast from "react-hot-toast";

export function useUpdateProduct() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: updateProduct, isLoading: isUpdating } = useMutation({
    mutationFn: (product) => editProductFn(jwtToken, product),
    onSuccess: () => {
      toast.success("Product updated");
      queryClient.invalidateQueries({ queryKey: ["inventory"] });
    },
    onError: (error) => {
      console.error("Create Product Error:", error);
      toast.error("Error updating product");
    },
  });

  return { updateProduct, isUpdating };
}
