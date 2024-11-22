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
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";

const headers = ["ID", "Item", "Type", "Quantity", "Date"];

function RecentMovements({ limit = 4 }) {
  const isFullPage = limit === -1;
  const { isLoading, data: movements } = useInventoryMovements();
  const navigate = useNavigate();

  if (isLoading) {
    return <SpinnerFS />;
  }

  return (
    <div className={`relative col-span-2 ${limit == -1 && "p-4 sm:p-6"}`}>
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
                <th key={header} className="text-left">
                  {header}
                </th>
              )}
            />
            <Table.Body
              data={!isFullPage ? movements.slice(0, limit) : movements}
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

      {!isFullPage && (
        <div className="absolute right-8 top-6 flex justify-end">
          <Button
            icon={<ArrowRightIcon className="ml-2 h-4 w-4" />}
            bgColor="bg-gray-800"
            textColor="text-gray-100"
            rounded="rounded-xl"
            className="flex-row-reverse"
            onClick={() => navigate("/movements")}
          >
            View All Movements
          </Button>
        </div>
      )}
    </div>
  );
}

RecentMovements.propTypes = {
  limit: PropTypes.number,
};

export default RecentMovements;
