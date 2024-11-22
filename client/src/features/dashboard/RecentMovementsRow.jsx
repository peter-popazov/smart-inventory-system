import { capitalize } from "@/utils/utils";
import PropTypes from "prop-types";

const colors = {
  Purchase: "bg-sky-100 text-sky-600",
  Sale: "bg-emerald-100 text-emerald-600",
  Adjustment: "bg-orange-100 text-orange-40",
};

function RecentMovementsRow({ movement }) {
  const movementType = capitalize(movement.movementType);
  return (
    <>
      <td>{movement.stockMovementId}</td>
      <td>{movement.productName}</td>
      <td>
        <span className={`rounded-xl p-1.5 px-2 ${colors[movementType]}`}>
          {movementType}
        </span>
      </td>
      <td>{movement.quantity}</td>
      <td>{movement.date}</td>
    </>
  );
}

RecentMovementsRow.propTypes = {
  movement: PropTypes.object.isRequired,
};

export default RecentMovementsRow;
