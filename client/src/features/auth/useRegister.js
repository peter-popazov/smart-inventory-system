import toast from "react-hot-toast";
import axios from "axios";
import { baseUrl } from "../../constants/baseUrl";
import { useMutation } from "react-query";
import { register as registerFn } from "./apiAuth";
import { useNavigate } from "react-router-dom";

export function useRegister() {
  const navigate = useNavigate();

  const { mutate: registerApi, isLoading } = useMutation({
    mutationFn: async ({ email, password, role }) => {
      const userExists = await axios.get(`${baseUrl}/auth/exists/${email}`);
      if (userExists.data) {
        return Promise.reject(
          new Error("User with this email already exists."),
        );
      }

      const response = await registerFn(email, password, role);
      return response;
    },
    onSuccess: () => {
      toast.success("Check your email to activate your account.");
      navigate("/auth/login");
    },
    onError: (error) => {
      const errorMessages = error.response?.data?.errors;

      if (errorMessages) {
        const messages = Object.values(errorMessages);
        const formattedErrors = messages.join("\n");
        toast.error("Errors:\n" + formattedErrors);
      } else {
        toast.error("Error: " + error.message || "Unknown error");
      }
    },
  });

  return { registerApi, isLoading };
}
