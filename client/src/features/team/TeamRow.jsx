import Button from "@/ui/button";
import { ICONS_SIZE_SM } from "@/constants/iconSize";
import { FaChevronRight } from "react-icons/fa6";
import PropTypes from "prop-types";
import ManageTeam from "./ManageTeam";
import Modal from "@/ui/Modal";

function TeamRow({ team }) {
  return (
    <>
      <td className="font-medium">{team.teamName}</td>
      <td>{team.teamSize}</td>
      <td>{team.teamDescription}</td>
      <td>
        <Modal>
          <Modal.Open opens="team-details">
            <Button
              size="sm"
              textColor="text-gray-700"
              bgColor="bg-none"
              rounded="rounded-lg"
              className="flex-row-reverse border border-gray-300 !text-[12px] hover:bg-gray-100"
              icon={<FaChevronRight size={ICONS_SIZE_SM - 8} />}
            >
              View details
            </Button>
          </Modal.Open>
          <Modal.Window name="team-details" width="!w-full">
            <ManageTeam team={team} />
          </Modal.Window>
        </Modal>
      </td>
    </>
  );
}
TeamRow.propTypes = {
  team: PropTypes.object.isRequired,
};

export default TeamRow;
