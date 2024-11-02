import { useMutation, useQueryClient } from "react-query";
import { createCategory as createCategoryFn } from "./apiCategory";
import toast from "react-hot-toast";

export function useCreateCategory() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: createCategory, isLoading: isCreateing } = useMutation({
    mutationFn: (category) => createCategoryFn(jwtToken, category),
    onSuccess: () => {
      toast.success("Category created successfully.");
      queryClient.invalidateQueries({ queryKey: ["categories"] });
    },
    onError: () => {
      toast.error("Error creating category.");
    },
  });

  return { createCategory, isCreateing };
}
