import { useMutation, useQueryClient } from "react-query";
import { getRefilmentEmail as getRefilmentEmailFn } from "./apiRefilment";
import toast from "react-hot-toast";

export function useRefilmentEmail() {
  const queryClient = useQueryClient();
  const authData = queryClient.getQueryData("user");
  const jwtToken = authData?.jwt_token;

  const { mutate: getRefilmentEmail } = useMutation({
    mutationFn: (productId) => getRefilmentEmailFn(jwtToken, productId),
    onSuccess: () => {
      toast.success("Check your email for the refilment document");
    },
    onError: () => {
      toast.error("Error sending email");
    },
  });

  return { getRefilmentEmail };
}
