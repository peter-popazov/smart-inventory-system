import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useState } from "react";
import InventoryStock from "./InventoryStock";
import ProductsForCategory from "./ProductsForCategory";

function Charts() {
  const [selectedTab, setSelectedTab] = useState("overview");
  return (
    <Tabs value={selectedTab} onValueChange={setSelectedTab}>
      <TabsList>
        <TabsTrigger value="overview">Overview</TabsTrigger>
        <TabsTrigger value="categories">Categories</TabsTrigger>
      </TabsList>

      <TabsContent value="overview" className="space-y-4">
        <InventoryStock />
      </TabsContent>

      <TabsContent value="categories" className="space-y-4">
        <ProductsForCategory />
      </TabsContent>
    </Tabs>
  );
}

export default Charts;
