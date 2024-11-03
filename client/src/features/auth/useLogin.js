import toast from "react-hot-toast";
import { useMutation, useQueryClient } from "react-query";
import { useNavigate } from "react-router-dom";
import { login as loginFn } from "./apiAuth";

export function useLogin() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { mutate: login, isLoading } = useMutation({
    mutationFn: ({ email, password }) => loginFn(email, password),
    onSuccess: (user) => {
      toast.success("Login successful.");
      queryClient.setQueryData("user", user);
      navigate("/dashboard");
    },
    onError: (error) => {
      toast.error("Login failed. \nError: " + error.response.data.error);
    },
  });

  return { login, isLoading };
}
