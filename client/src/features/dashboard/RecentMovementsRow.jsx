import PropTypes from "prop-types";

function RecentMovementsRow({ movement }) {
  return (
    <>
      <td className="pl-4">{movement.id}</td>
      <td className="pl-4">{movement.item}</td>
      <td className="pl-4">{movement.type}</td>
      <td className="pl-4">{movement.quantity}</td>
      <td className="pl-4">{movement.date}</td>
    </>
  );
}

RecentMovementsRow.propTypes = {
  movement: PropTypes.object.isRequired,
};

export default RecentMovementsRow;
