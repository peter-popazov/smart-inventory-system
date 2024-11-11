import Charts from "@/features/dashboard/Charts";
import Cards from "@/features/dashboard/Cards";
import { Package } from "lucide-react";
import PageHeader from "@/ui/PageHeader";
import RecentMovements from "@/features/dashboard/RecentMovements";

export default function InventoryDashboard() {
  return (
    <>
      <PageHeader icon={<Package />}>Dashboard</PageHeader>
      <main className="gap-6 space-y-6 p-4 sm:p-6 md:grid md:grid-cols-[5fr_2.5fr] lg:grid-cols-[5fr_2fr]">
        <Charts />
        <Cards />
        <RecentMovements />
      </main>
    </>
  );
}
