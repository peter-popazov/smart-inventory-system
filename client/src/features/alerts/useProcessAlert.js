import { useMutation, useQueryClient } from "react-query";
import { processAlert as processAlertFn } from "./apiAlerts";
import toast from "react-hot-toast";

export function useProcessAlert() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: processAlert, isLoading: isUpdating } = useMutation({
    mutationFn: (alertId) => processAlertFn(jwtToken, alertId),
    onSuccess: () => {
      toast.success("Alert processed successfully");
      queryClient.invalidateQueries({
        queryKey: [
          "alerts",
          "inventory",
          "dashboardStats",
          "inventoryMovements",
        ],
      });
    },
    onError: () => {
      toast.error("Error processing alert");
    },
  });

  return { processAlert, isUpdating };
}
