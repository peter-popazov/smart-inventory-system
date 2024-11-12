import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Input from "@/ui/Input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { CiSearch } from "react-icons/ci";
import PropTypes from "prop-types";
import { useState } from "react";

import Table from "@/ui/Table";
import AlertsRow from "./AlertsRow";

const headers = ["Item", "Current Stock", "Reorder Point", "Price", ""];

function AlertsTable({ stockAlerts }) {
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("currentQuantity");

  const filteredAlerts = stockAlerts
    .filter((alert) =>
      alert.productName.toLowerCase().includes(searchTerm.toLowerCase()),
    )
    .sort((a, b) => {
      if (sortBy === "currentQuantity") {
        return a.currentQuantity - b.currentQuantity;
      } else if (sortBy === "reorderStock") {
        return (
          a.reorderStock - a.currentQuantity - (b.reorderStock - b.currentQuantity)
        );
      }
      return 0;
    });

  return (
    <Card>
      <CardHeader>
        <CardTitle>Low Stock Items</CardTitle>
        <CardDescription>Items that need to be reordered soon</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="mb-4 flex items-center justify-between">
          <div className="relative w-64">
            <Input
              placeholder="Search items or suppliers"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full h-10"
              icon={<CiSearch size={18} />}
            />
          </div>
          <Select value={sortBy} onValueChange={setSortBy}>
            <SelectTrigger className="w-[250px]">
              <SelectValue placeholder="Sort by" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="currentQuantity">
                Current Stock (Low to High)
              </SelectItem>
              <SelectItem value="reorderStock">Reorder Priority</SelectItem>
            </SelectContent>
          </Select>
        </div>
        <Table cols="grid-cols-[1.5fr_1fr_1fr_1.5fr_1fr]">
          <Table.Header
            data={headers}
            render={(header) => (
              <th key={header} className="text-left">
                {header}
              </th>
            )}
          />
          <Table.Body
            data={filteredAlerts}
            render={(alert) => <AlertsRow key={alert.alertId} alert={alert} />}
          />
        </Table>
      </CardContent>
    </Card>
  );
}

export default AlertsTable;

AlertsTable.propTypes = {
  stockAlerts: PropTypes.array.isRequired,
};
