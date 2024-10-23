import {
  Bar,
  BarChart,
  Line,
  LineChart,
  ResponsiveContainer,
  XAxis,
  YAxis,
} from "recharts";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";
import { useState } from "react";

const inventoryData = [
  { date: "2023-01", level: 1000 },
  { date: "2023-02", level: 1200 },
  { date: "2023-03", level: 900 },
  { date: "2023-04", level: 1500 },
  { date: "2023-05", level: 1800 },
  { date: "2023-06", level: 2000 },
];

const categoryData = [
  { name: "Electronics", value: 35 },
  { name: "Clothing", value: 25 },
  { name: "Home Goods", value: 20 },
  { name: "Books", value: 15 },
  { name: "Other", value: 5 },
];

function Charts() {
  const [selectedTab, setSelectedTab] = useState("overview");
  return (
    <Tabs value={selectedTab} onValueChange={setSelectedTab}>
      <TabsList>
        <TabsTrigger value="overview">Overview</TabsTrigger>
        <TabsTrigger value="categories">Categories</TabsTrigger>
      </TabsList>

      <TabsContent value="overview" className="space-y-4">
        <Card>
          <CardHeader>
            <CardTitle>Inventory Levels Over Time</CardTitle>
            <CardDescription>
              Total number of items in stock per month
            </CardDescription>
          </CardHeader>
          <CardContent>
            <ChartContainer
              config={{
                level: {
                  label: "Inventory Level",
                  color: "hsl(var(--chart-1))",
                },
              }}
              className="h-[200px] w-full xl:h-[300px]"
            >
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={inventoryData}>
                  <XAxis dataKey="date" />
                  <YAxis />
                  <ChartTooltip content={<ChartTooltipContent />} />
                  <Line
                    type="monotone"
                    dataKey="level"
                    stroke="var(--color-level)"
                    strokeWidth={2}
                  />
                </LineChart>
              </ResponsiveContainer>
            </ChartContainer>
          </CardContent>
        </Card>
      </TabsContent>

      <TabsContent value="categories" className="space-y-4">
        <Card>
          <CardHeader>
            <CardTitle>Inventory by Category</CardTitle>
            <CardDescription>
              Distribution of items across categories
            </CardDescription>
          </CardHeader>
          <CardContent>
            <ChartContainer
              config={{
                value: {
                  label: "Percentage",
                  color: "hsl(var(--chart-1))",
                },
              }}
              className="h-[300px]"
            >
              <ResponsiveContainer width="10%" height="100%">
                <BarChart data={categoryData}>
                  <XAxis dataKey="name" />
                  <YAxis />
                  <ChartTooltip content={<ChartTooltipContent />} />
                  <Bar dataKey="value" fill="var(--color-value)" />
                </BarChart>
              </ResponsiveContainer>
            </ChartContainer>
          </CardContent>
        </Card>
      </TabsContent>
    </Tabs>
  );
}

export default Charts;
