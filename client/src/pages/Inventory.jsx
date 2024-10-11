import Table from "../ui/Table";
import TableActions from "../ui/TableActions";
import InventoryRow from "../features/inventory/InventoryRow";

const headers = [
  "SKU",
  "Product",
  "Price",
  "Quantity",
  "Category",
  "Reorder Level",
  "Provider",
  "Action",
];

function Inventory() {
  return (
    <div className="mx-6 sm:mx-8 md:mx-12 lg:mx-16">
      <h1 className="my-8 mt-10 text-3xl font-bold lg:text-4xl">Inventory</h1>
      <div className="shadow-md shadow-purple-200">
        <TableActions />
        <Table cols="grid-cols-[1fr_1.5fr_0.6fr_0.6fr_1fr_1fr_1fr_0.6fr]">
          <Table.Header headers={headers} />
          <Table.Body>
            <InventoryRow />
            <InventoryRow />
            <InventoryRow />
            <InventoryRow />
          </Table.Body>
          <Table.Footer>
            <span>PAGINATION</span>
          </Table.Footer>
        </Table>
      </div>
    </div>
  );
}

export default Inventory;
