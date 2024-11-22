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
import { ICONS_SIZE_SM } from "@/constants/iconSize";

const headers = ["Item", "Current Stock", "Reorder Point", "Price", "Reorder Form", "Process"];

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
          a.reorderStock -
          a.currentQuantity -
          (b.reorderStock - b.currentQuantity)
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
        <div className="mb-4 flex flex-col items-center justify-between gap-4 sm:flex-row">
          <div className="flex w-full flex-col items-center gap-4 sm:w-auto sm:flex-row">
            <Input
              placeholder="Search items or suppliers"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="h-9 w-full sm:h-10 sm:w-[250px]"
              icon={<CiSearch size={ICONS_SIZE_SM} />}
            />

            <Select
              value={sortBy}
              onValueChange={setSortBy}
              className="w-full sm:w-[250px]"
            >
              <SelectTrigger className="w-full sm:w-[250px]">
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
        </div>

        <Table cols="grid-cols-[2fr_1fr_1fr_1.5fr_1fr_0.5fr]">
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
