import { useMutation, useQueryClient } from "react-query";
import { updateUser as updateUserFn } from "./apiUser";
import toast from "react-hot-toast";

export function useUpdateUser() {
  const queryClient = useQueryClient();
  const authData = queryClient.getQueryData("user");
  const jwtToken = authData?.jwt_token;

  const { mutate: updateUser, isLoading: isUpdating } = useMutation({
    mutationFn: (userData) => updateUserFn(jwtToken, userData),
    onSuccess: (userData) => {
      toast.success("User updated");
      queryClient.invalidateQueries("user");
      queryClient.setQueryData("user", {
        ...authData,
        ...userData,
      });
    },
    onError: () => {
      toast.error("Error updating user");
    },
  });

  return { updateUser, isUpdating };
}
