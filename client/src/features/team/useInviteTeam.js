import { addTeam as addTeamFn } from "./apiTeam";
import { useMutation, useQueryClient } from "react-query";
import toast from "react-hot-toast";

export function useAddTeam() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const jwtToken = user?.jwt_token;

  const { mutate: addTeam, isLoading: isAdding } = useMutation({
    mutationFn: (data) => addTeamFn(jwtToken, data),
    onSuccess: (data) => {
      toast.success(`User ${data.email} added to team`);
      queryClient.invalidateQueries({ queryKey: ["teams"] });
    },
    onError: (error) => {
      toast.error(error.response.data.businessErrorDesc);
    },
  });

  return { addTeam, isAdding };
}
