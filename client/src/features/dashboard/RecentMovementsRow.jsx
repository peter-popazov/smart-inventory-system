import PropTypes from "prop-types";

function RecentMovementsRow({ movement }) {
  return (
    <>
      <td className="pl-4">{movement.stockMovementId}</td>
      <td className="pl-4">{movement.productName}</td>
      <td className="pl-4">{movement.movementType}</td>
      <td className="pl-4">{movement.quantity}</td>
      <td className="pl-4">{movement.date}</td>
    </>
  );
}

RecentMovementsRow.propTypes = {
  movement: PropTypes.object.isRequired,
};

export default RecentMovementsRow;
