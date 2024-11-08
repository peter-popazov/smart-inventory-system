import { useMutation, useQueryClient } from "react-query";
import { removeTeamMember as removeTeamMemberFn } from "./apiTeam";
import toast from "react-hot-toast";

export function useDeleteMember() {
  const queryClient = useQueryClient();
  const authData = queryClient.getQueryData("user");
  const jwtToken = authData?.jwt_token;

  const { mutate: deleteMember, isLoading: isDeleting } = useMutation({
    mutationFn: (data) => removeTeamMemberFn(jwtToken, data),
    onSuccess: () => {
      toast.success(`Member was deleted`);
      queryClient.invalidateQueries("teams");
    },
    onError: () => {
      toast.error(`Error deleting member`);
    },
  });

  return { deleteMember, isDeleting };
}
