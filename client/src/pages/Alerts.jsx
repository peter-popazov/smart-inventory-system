import { IoAlertCircleOutline } from "react-icons/io5";
import PageHeader from "@/ui/PageHeader";
import AlertCards from "@/features/alerts/AlertCards";
import AlertsTable from "@/features/alerts/AlertsTable";
import { useAlerts } from "@/features/alerts/useAlerts";
import SpinnerFS from "@/ui/SpinnerFS";

export default function Alerts() {
  const { data: stockAlerts, isLoading } = useAlerts();

  if (isLoading) {
    return <SpinnerFS />;
  }

  return (
    <div className="flex min-h-screen flex-col">
      <PageHeader icon={<IoAlertCircleOutline size={24} />}>
        Stock alerts
      </PageHeader>
      <div className="flex-1 space-y-6 p-4 md:p-6">
        <AlertCards stockAlerts={stockAlerts} />
        <AlertsTable stockAlerts={stockAlerts} />
      </div>
    </div>
  );
}
