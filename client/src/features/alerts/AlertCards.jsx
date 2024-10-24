import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowDownIcon, ArrowUpIcon } from "lucide-react";
import PropTypes from "prop-types";

function AlertCards({ stockAlerts }) {
  return (
    <div className="grid gap-4 md:grid-cols-2">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">Total Alerts</CardTitle>
          <ArrowUpIcon className="h-4 w-4 text-red-600" />
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">{stockAlerts.length}</div>
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
          <ArrowDownIcon className="h-4 w-4 text-green-600" />
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">%$24,500%</div>
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
