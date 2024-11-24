/* eslint-disable react/prop-types */
import {
  Bar,
  BarChart,
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
import { useProductsForCategory } from "./useProductsForCategory";
import SpinnerFS from "@/ui/SpinnerFS";
import NoDataAvailable from "@/ui/NoDataAvailable";

const CustomTooltip = ({ active, payload }) => {
  if (active && payload && payload.length) {
    return (
      <div className="rounded-md border border-gray-100 bg-white px-4 py-2 text-gray-800 shadow-lg">
        <p className="text-sm font-semibold">{payload[0].payload.name}</p>
        <p className="text-xs">Items: {payload[0].value}</p>
      </div>
    );
  }
  return null;
};

function ProductsForCategory() {
    const { data: categoriesData, isLoading } = useProductsForCategory();
  
    if (categoriesData.length === 0) {
      return <NoDataAvailable />;
    }

    if (isLoading) {
      return <SpinnerFS />;
    }
  
    return (
      <Card>
        <CardHeader>
          <CardTitle>Inventory by Category</CardTitle>
          <CardDescription>
            Distribution of items across categories
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="h-[200px] w-full text-sm sm:h-[250px] md:h-[300px] lg:h-[350px]">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={categoriesData}>
                <XAxis dataKey="categoryName" /> 
                <YAxis /> 
                <Tooltip cursor={{ fill: "#f1f3f5" }} content={<CustomTooltip />} />
                <Bar dataKey="productsNumber" fill="rgb(167 139 250)" /> 
              </BarChart>
            </ResponsiveContainer>
          </div>
        </CardContent>
      </Card>
    );
  }

export default ProductsForCategory;
