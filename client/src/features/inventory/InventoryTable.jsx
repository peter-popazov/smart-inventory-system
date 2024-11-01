import { useState } from "react";
import Table from "../../ui/Table";
import TableActions from "../../ui/TableActions";
import InventoryRow from "./InventoryRow";
import Menus from "@/ui/Menus";
import { useInventory } from "./useInventory";
import { transformData } from "../../utils/utils";

const headers = [
  "SKU",
  "Product",
  "Price",
  "Quantity",
  "Category",
  "Reorder Level",
  "Provider",
];

function InventoryTable() {
  const [query, setQuery] = useState("");
  const { isLoading, inventory } = useInventory();
  const inventoryTransformed = Array.isArray(inventory)
    ? transformData(inventory).filter((item) =>
        item.product.toLowerCase().includes(query.toLowerCase()),
      )
    : [];

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="mx-2 mt-2 shadow-md shadow-purple-200 lg:mx-6 lg:mt-6">
      <TableActions query={query} setQuery={setQuery} />
      <Menus>
        <Table cols="grid-cols-[0.6fr_1.5fr_0.6fr_0.6fr_1fr_1fr_1fr_0.2fr]">
          <Table.Header
            data={headers}
            render={(header, index) => (
              <th key={index} className="text-left">
                {header}
              </th>
            )}
          />
          <Table.Body
            data={inventoryTransformed}
            render={(item, index) => <InventoryRow key={index} item={item} />}
          />
          <Table.Footer>
            <span>PAGINATION</span>
          </Table.Footer>
        </Table>
      </Menus>
    </div>
  );
}

export default InventoryTable;
