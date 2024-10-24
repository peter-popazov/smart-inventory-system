import { IoAlertCircleOutline } from "react-icons/io5";

import PageHeader from "@/ui/PageHeader";
import AlertCards from "@/features/alerts/AlertCards";
import AlertsTable from "@/features/alerts/AlertsTable";

const stockAlerts = [
  {
    id: "001",
    item: "Laptop",
    currentStock: 10,
    reorderPoint: 15,
    supplier: "TechCorp",
  },
  {
    id: "002",
    item: "T-shirt",
    currentStock: 25,
    reorderPoint: 50,
    supplier: "FashionInc",
  },
  {
    id: "003",
    item: "Coffee Maker",
    currentStock: 5,
    reorderPoint: 10,
    supplier: "HomeGoods",
  },
  {
    id: "004",
    item: "Smartphone",
    currentStock: 8,
    reorderPoint: 20,
    supplier: "ElectronicsPro",
  },
  {
    id: "005",
    item: "Desk Chair",
    currentStock: 3,
    reorderPoint: 8,
    supplier: "OfficeFurniture",
  },
  {
    id: "006",
    item: "Printer Paper",
    currentStock: 50,
    reorderPoint: 100,
    supplier: "OfficeSupplies",
  },
  {
    id: "007",
    item: "Wireless Mouse",
    currentStock: 12,
    reorderPoint: 25,
    supplier: "ComputerAccessories",
  },
  {
    id: "008",
    item: "LED Monitor",
    currentStock: 6,
    reorderPoint: 15,
    supplier: "DisplayTech",
  },
];

export default function Messages() {
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
