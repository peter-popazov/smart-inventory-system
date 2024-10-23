import Charts from "@/features/dashboard/Charts";
import Cards from "@/features/dashboard/Cards";
import { Package } from "lucide-react";
import PageHeader from "@/ui/PageHeader";
import RecentMovements from "@/features/dashboard/RecentMovements";

export default function InventoryDashboard() {
  return (
    <>
      <PageHeader icon={<Package />}>Dashboard</PageHeader>
      <main className="grid gap-x-8 gap-y-8 p-4 md:grid-cols-[5fr_3fr] md:grid-rows-1 md:p-6">
        <Charts />
        <Cards />
        <RecentMovements />
      </main>
    </>
  );
}
