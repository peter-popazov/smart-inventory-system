import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { ArrowRightIcon } from "lucide-react";
import Button from "@/ui/Button";

const recentMovements = [
  {
    id: "001",
    item: "Laptop",
    type: "Inbound",
    quantity: 50,
    date: "2023-06-15",
  },
  {
    id: "002",
    item: "T-shirt",
    type: "Outbound",
    quantity: 100,
    date: "2023-06-14",
  },
  {
    id: "003",
    item: "Coffee Maker",
    type: "Inbound",
    quantity: 30,
    date: "2023-06-13",
  },
  {
    id: "004",
    item: "Novel",
    type: "Outbound",
    quantity: 75,
    date: "2023-06-12",
  },
  {
    id: "005",
    item: "Headphones",
    type: "Inbound",
    quantity: 200,
    date: "2023-06-11",
  },
];

function RecentMovements() {
  return (
    <div className="relative col-span-2">
      <Card>
        <CardHeader>
          <CardTitle>Recent Inventory Movements</CardTitle>
          <CardDescription>
            Latest inbound and outbound transactions
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Item</TableHead>
                <TableHead>Type</TableHead>
                <TableHead>Quantity</TableHead>
                <TableHead>Date</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {recentMovements.map((movement) => (
                <TableRow key={movement.id}>
                  <TableCell>{movement.id}</TableCell>
                  <TableCell>{movement.item}</TableCell>
                  <TableCell>{movement.type}</TableCell>
                  <TableCell>{movement.quantity}</TableCell>
                  <TableCell>{movement.date}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <div className="absolute right-8 top-6 flex justify-end">
        <Button
          icon={<ArrowRightIcon className="ml-2 h-4 w-4" />}
          bgColor="bg-gray-800"
          textColor="text-gray-100"
          rounded="rounded-xl"
          className="flex-row-reverse"
        >
          View All Movements
        </Button>
      </div>
    </div>
  );
}

export default RecentMovements;
