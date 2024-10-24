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

const headers = [
  "Item",
  "Current Stock",
  "Reorder Point",
  "Supplier",
  "",
];

function AlertsTable({ stockAlerts }) {
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("currentStock");

  const filteredAlerts = stockAlerts
    .filter(
      (alert) =>
        alert.item.toLowerCase().includes(searchTerm.toLowerCase()) ||
        alert.supplier.toLowerCase().includes(searchTerm.toLowerCase()),
    )
    .sort((a, b) => {
      if (sortBy === "currentStock") {
        return a.currentStock - b.currentStock;
      } else if (sortBy === "reorderPoint") {
        return (
          a.reorderPoint - a.currentStock - (b.reorderPoint - b.currentStock)
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
              className="w-full"
              icon={<CiSearch size={18} />}
            />
          </div>
          <Select value={sortBy} onValueChange={setSortBy}>
            <SelectTrigger className="w-[250px]">
              <SelectValue placeholder="Sort by" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="currentStock">
                Current Stock (Low to High)
              </SelectItem>
              <SelectItem value="reorderPoint">Reorder Priority</SelectItem>
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
            render={(alert) => <AlertsRow alert={alert} />}
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
