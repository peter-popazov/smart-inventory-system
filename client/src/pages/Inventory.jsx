import InventoryTable from "@/features/inventory/InventoryTable";
import PageHeader from "@/ui/PageHeader";
import { MdOutlineDashboard } from "react-icons/md";

function Inventory() {
  return (
    <div className="h-screen text-gray-800">
      <PageHeader icon={<MdOutlineDashboard size={24} />}>Inventory</PageHeader>
      <InventoryTable />
    </div>
  );
}

export default Inventory;
