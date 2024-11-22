import Charts from "@/features/dashboard/Charts";
import Cards from "@/features/dashboard/Cards";
import { MdOutlineDashboard } from "react-icons/md";
import PageHeader from "@/ui/PageHeader";
import RecentMovements from "@/features/dashboard/RecentMovements";

export default function InventoryDashboard() {
  return (
    <>
      <PageHeader icon={<MdOutlineDashboard size={24} />}>Dashboard</PageHeader>
      <div className="gap-4 space-y-2 p-4 sm:p-6 md:grid md:grid-cols-[5fr_2.5fr] lg:grid-cols-[5fr_2fr]">
        <Charts />
        <Cards />
        <RecentMovements />
      </div>
    </>
  );
}
