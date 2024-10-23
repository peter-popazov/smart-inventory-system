import Table from "../../ui/Table";
import TableActions from "../../ui/TableActions";
import InventoryRow from "./InventoryRow";
import Menus from "@/ui/Menus";

const headers = [
  "SKU",
  "Product",
  "Price",
  "Quantity",
  "Category",
  "Reorder Level",
  "Provider",
];

const sampleData = [
  {
    SKU: "SKU12345",
    product: "Laptop",
    price: 1200,
    quantity: 10,
    category: "Electronics",
    reorderLevel: 5,
    provider: "TechProvider Inc.",
  },
  {
    SKU: "SKU67890",
    product: "Smartphone",
    price: 800,
    quantity: 50,
    category: "Mobile Devices",
    reorderLevel: 20,
    provider: "MobileWorld",
  },
  {
    SKU: "SKU11223",
    product: "Desk Chair",
    price: 150,
    quantity: 210,
    category: "Furniture",
    reorderLevel: 50,
    provider: "FurniCo",
  },
  {
    SKU: "SKU44556",
    product: "Wireless Headphones",
    price: 300,
    quantity: 705,
    category: "Accessories",
    reorderLevel: 30,
    provider: "SoundGear",
  },
  {
    SKU: "SKU77889",
    product: "Monitor",
    price: 400,
    quantity: 250,
    category: "Electronics",
    reorderLevel: 10,
    provider: "DisplayTech",
  },
  {
    SKU: "SKU99001",
    product: "Office Desk",
    price: 600,
    quantity: 400,
    category: "Furniture",
    reorderLevel: 15,
    provider: "Workspace Solutions",
  },
];

function InventoryTable() {
  return (
    <div className="mx-2 mt-2 shadow-md shadow-purple-200 lg:mx-6 lg:mt-6">
      <TableActions />
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
            data={sampleData}
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
