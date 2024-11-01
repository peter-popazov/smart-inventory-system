import { useMutation, useQueryClient } from "react-query";
import { deleteProduct as deleteProductFn } from "./apiInventory";
import toast from "react-hot-toast";

export function useDeleteProduct() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: deleteProduct, isLoading: isDeleting } = useMutation({
    mutationFn: (id) => deleteProductFn(jwtToken, id),
    onSuccess: () => {
      toast.success("Product deleted");
      queryClient.invalidateQueries({ queryKey: ["inventory"] });
    },
    onError: () => {
      toast.error("Error deleting product");
    },
  });

  return { deleteProduct, isDeleting };
}
