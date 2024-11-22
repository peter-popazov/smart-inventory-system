import PageHeader from "@/ui/PageHeader";
import RecentMovements from "../features/dashboard/RecentMovements";
import { TbHomeMove } from "react-icons/tb";

function AllMovements() {
  return (
    <>
      <PageHeader icon={<TbHomeMove size={24} />}>All Movements</PageHeader>
      <RecentMovements limit={-1} />
    </>
  );
}

export default AllMovements;
