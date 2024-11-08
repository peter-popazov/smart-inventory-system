import { useMutation, useQueryClient } from "react-query";
import { createTeam as createTeamFn } from "./apiTeam";
import toast from "react-hot-toast";

export function useCreateTeam() {
  const queryClient = useQueryClient();
  const authData = queryClient.getQueryData("user");
  const jwtToken = authData?.jwt_token;

  const { mutate: createTeam, isLoading: isCreating } = useMutation({
    mutationFn: (data) => createTeamFn(jwtToken, data),
    onSuccess: (userData) => {
      toast.success(`Team "${userData.teamName}" was created`);
      queryClient.invalidateQueries("teams");
    },
    onError: () => {
      toast.error("Error creating team");
    },
  });

  return { createTeam, isCreating };
}
