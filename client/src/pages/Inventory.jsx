import InventoryTable from "@/features/inventory/InventoryTable";

function Inventory() {
  return (
    <div className="mx-2 lg:mx-6">
      <h1 className="my-4 mt-4 text-2xl font-bold lg:my-8 lg:mt-8 lg:text-3xl">
        Inventory
      </h1>
      <InventoryTable />
    </div>
  );
}

export default Inventory;
