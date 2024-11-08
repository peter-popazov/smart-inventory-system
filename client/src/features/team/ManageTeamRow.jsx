import Button from "@/ui/Button";
import { getFullName } from "@/utils/utils";
import PropTypes from "prop-types";
import { useDeleteMember } from "./useDeleteMember";
import Spinner from "@/ui/Spinner";

function ManageTeamRow({ member, user, teamId }) {
  const { deleteMember, isDeleting } = useDeleteMember();

  if (isDeleting) {
    return <Spinner />;
  }

  return (
    <>
      <td>{`${member.firstName || member.lastName ? getFullName(member) : "No name"}`}</td>
      <td>{member.email}</td>
      <td>{member.role}</td>
      <td>
        {user?.email !== member.email && (
          <Button
            size="sm"
            textColor="text-red-600"
            bgColor="bg-none"
            rounded="rounded-lg"
            className="border border-red-600 !text-[12px] hover:bg-red-50"
            onClick={() =>
              deleteMember({
                userEmail: member.email,
                teamId: teamId,
              })
            }
          >
            Delete
          </Button>
        )}
      </td>
    </>
  );
}

ManageTeamRow.propTypes = {
  member: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired,
  teamId: PropTypes.number.isRequired,
};

export default ManageTeamRow;
