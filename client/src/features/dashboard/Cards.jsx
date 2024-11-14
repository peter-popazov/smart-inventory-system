import { ArrowUpIcon } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useDashboardStats } from "./useDashboardStats";
import SpinnerFS from "@/ui/SpinnerFS";
import { formatCurrency } from "@/utils/utils";

function Cards() {
  const { isLoading, data: dahboardStats } = useDashboardStats();

  if (isLoading) {
    return <SpinnerFS />;
  }

  return (
    <div className="grid gap-4 text-gray-800">
      <Card className="col-span-2">
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">Total Income</CardTitle>
          <ArrowUpIcon className="h-4 w-4 text-green-600" />
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">
            {formatCurrency(dahboardStats.totalIncome)}
          </div>
          <p className="text-xs text-muted-foreground">
            %+20.1%% from last month
          </p>
        </CardContent>
      </Card>

      <Card className="col-span-2">
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">Inventory Value</CardTitle>
          <ArrowUpIcon className="h-4 w-4 text-green-600" />
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">
            {formatCurrency(dahboardStats.inventoryValue)}
          </div>
          <p className="text-xs text-muted-foreground">
            % +12.5% from last month%
          </p>
        </CardContent>
      </Card>

      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">Total Items</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">{dahboardStats.totalItems}</div>
          <p className="text-xs text-muted-foreground">Overall stock levels</p>
        </CardContent>
      </Card>

      <Card>
        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
          <CardTitle className="text-sm font-medium">Team Size</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="text-2xl font-bold">{dahboardStats.teamSize}</div>
          <p className="text-xs text-muted-foreground">You are admin for</p>
        </CardContent>
      </Card>
    </div>
  );
}

export default Cards;
