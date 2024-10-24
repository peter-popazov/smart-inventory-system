import PropTypes from "prop-types";
import Button from "@/ui/Button";

function AlertsRow({ alert }) {
  return (
    <>
      <td className="font-medium">{alert.item}</td>
      <td className="pl-2">{alert.currentStock}</td>
      <td className="pl-2">{alert.reorderPoint}</td>
      <td className="pl-2">{alert.supplier}</td>
      <td>
        <Button className="text-gray-600">Reorder</Button>
      </td>
    </>
  );
}

AlertsRow.propTypes = {
  alert: PropTypes.object.isRequired,
};

export default AlertsRow;
