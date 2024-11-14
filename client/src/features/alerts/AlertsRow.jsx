import Button from "@/ui/Button";
import SpinnerFS from "@/ui/SpinnerFS";
import PropTypes from "prop-types";
import { MdOutlineDone } from "react-icons/md";
import { formatCurrency } from "@/utils/utils";
import { useRefilmentEmail } from "./useRefilmentEmail";
import { useProcessAlert } from "./useProcessAlert";

function AlertsRow({ alert }) {
  const { getRefilmentEmail } = useRefilmentEmail();
  const { processAlert, isUpdating } = useProcessAlert();
  if (isUpdating) {
    return <SpinnerFS />;
  }
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
          onClick={() => getRefilmentEmail(alert.productId)}
        >
          Reorder
        </Button>
      </td>
      <td>
        <Button
          size="iconOnly"
          textColor="text-gray-50"
          bgColor="bg-teal-500"
          rounded="rounded-lg"
          icon={<MdOutlineDone />}
          className="hover:bg-teal-600"
          onClick={() => processAlert(alert.alertId)}
        />
      </td>
    </>
  );
}

AlertsRow.propTypes = {
  alert: PropTypes.object.isRequired,
};

export default AlertsRow;
