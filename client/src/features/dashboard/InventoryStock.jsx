/* eslint-disable react/prop-types */
import {
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useInventoryStock } from "./useInventoryStock";
import SpinnerFS from "@/ui/SpinnerFS";

const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length) {
    const { name, level } = payload[0].payload;

    return (
      <div className="rounded-md bg-white px-4 py-2 text-gray-800 shadow-lg border border-gray-100">
        <p className="text-xs font-semibold">{name || label}</p>
        <p className="text-sm">Items: <span className="font-medium">{level || payload[0].value}</span></p>
      </div>
    );
  }
  return null;
};

function InventoryStock() {
  const { data: stockData, isLoading } = useInventoryStock();

  if (isLoading) {
    return <SpinnerFS />;
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Inventory Levels Over Time</CardTitle>
        <CardDescription>
          Total number of items in stock per month
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="h-[200px] w-full text-sm sm:h-[250px] md:h-[300px] lg:h-[350px]">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={stockData}>
              <XAxis dataKey="date" />
              <YAxis />
              <Tooltip content={<CustomTooltip />} />
              <Line
                type="monotone"
                dataKey="level"
                stroke="rgb(139 92 246)"
                strokeWidth={2}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </CardContent>
    </Card>
  );
}

export default InventoryStock;
