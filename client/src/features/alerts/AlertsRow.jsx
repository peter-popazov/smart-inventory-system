import PropTypes from "prop-types";
import Button from "@/ui/Button";
import { formatCurrency } from "@/utils/utils";

function AlertsRow({ alert }) {
  return (
    <>
      <td className="font-medium">{alert.productName}</td>
      <td className="pl-2">{alert.currentQuantity}</td>
      <td className="pl-2">{alert.reorderStock}</td>
      <td className="pl-2">{formatCurrency(alert.reorderPrice)}</td>
      <td>
        <Button
          textColor="text-gray-50"
          bgColor="bg-violet-500"
          rounded="rounded-lg"
          className="hover:bg-violet-600"
          onClick={() => console.log(alert.alertId)}
        >
          Reorder
        </Button>
      </td>
    </>
  );
}

AlertsRow.propTypes = {
  alert: PropTypes.object.isRequired,
};

export default AlertsRow;
