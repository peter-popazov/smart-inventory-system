import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { formatCurrency } from "@/utils/utils";
import { ArrowUpIcon } from "lucide-react";
import PropTypes from "prop-types";

function AlertCards({ stockAlerts }) {
  const alertsCount = stockAlerts.length;
  const totalReorderPrice = stockAlerts.reduce(
    (acc, alert) => acc + alert.reorderPrice,
    0,
  );

  return (
    <div className="grid gap-4 md:grid-cols-[1fr_2fr]">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">Total Alerts</CardTitle>
          {alertsCount > 0 && <ArrowUpIcon className="h-4 w-4 text-red-600" />}
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">{alertsCount}</div>
          <p className="text-xs text-muted-foreground">
            Items below reorder point
          </p>
        </CardContent>
      </Card>
      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">
            Estimated Restock Value
          </CardTitle>
          {alertsCount > 0 && <ArrowUpIcon className="h-4 w-4 text-red-600" />}
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">
            {formatCurrency(totalReorderPrice)}
          </div>
          <p className="text-xs text-muted-foreground">
            Cost to restock all low items
          </p>
        </CardContent>
      </Card>
    </div>
  );
}

AlertCards.propTypes = {
  stockAlerts: PropTypes.array,
};

export default AlertCards;
