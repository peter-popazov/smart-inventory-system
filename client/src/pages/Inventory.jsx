import InventoryTable from "@/features/inventory/InventoryTable";
import PageHeader from "@/ui/PageHeader";
import { Package } from "lucide-react";

function Inventory() {
  return (
    <div className="h-screen text-gray-800">
      <PageHeader icon={<Package />}>Inventory</PageHeader>
      <InventoryTable />
    </div>
  );
}

export default Inventory;
