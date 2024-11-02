import { useMutation, useQueryClient } from "react-query";
import { createWarehouse as createWarehouseFn } from "./apiWarehouse";
import toast from "react-hot-toast";

export function useCreateWarehouse() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: createWarehouse, isLoading: isCreateing } = useMutation({
    mutationFn: (warehouse) => createWarehouseFn(jwtToken, warehouse),
    onSuccess: () => {
      toast.success("Warehouse created successfully.");
      queryClient.invalidateQueries({ queryKey: ["warehouses"] });
    },
    onError: () => {
      toast.error("Error creating warehouse.");
    },
  });

  return { createWarehouse, isCreateing };
}
