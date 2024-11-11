import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { ArrowRightIcon } from "lucide-react";
import Button from "@/ui/Button";
import Table from "@/ui/Table";
import RecentMovementsRow from "./RecentMovementsRow";
import { useInventoryMovements } from "./useProductsMovements";
import SpinnerFS from "@/ui/SpinnerFS";

const headers = ["ID", "Item", "Type", "Quantity", "Date"];

function RecentMovements() {
  const { isLoading, data: movements } = useInventoryMovements();

  console.log(movements);
  if (isLoading) {
    return <SpinnerFS />;
  }
  return (
    <div className="relative col-span-2">
      <Card>
        <CardHeader>
          <CardTitle>Recent Inventory Movements</CardTitle>
          <CardDescription>
            Latest inbound and outbound transactions
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table cols="grid-cols-[0.5fr_1.5fr_1fr_1fr_1fr]">
            <Table.Header
              data={headers}
              render={(header) => (
                <th key={header} className="pl-4 text-left">
                  {header}
                </th>
              )}
            />
            <Table.Body
              data={movements}
              render={(movement) => (
                <RecentMovementsRow
                  key={movement.stockMovementId}
                  movement={movement}
                />
              )}
            />
          </Table>
        </CardContent>
      </Card>

      <div className="absolute right-8 top-6 flex justify-end">
        <Button
          icon={<ArrowRightIcon className="ml-2 h-4 w-4" />}
          bgColor="bg-gray-800"
          textColor="text-gray-100"
          rounded="rounded-xl"
          className="flex-row-reverse"
        >
          View All Movements
        </Button>
      </div>
    </div>
  );
}

export default RecentMovements;
