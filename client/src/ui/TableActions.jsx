import { useState } from "react";
import { BiExport } from "react-icons/bi";
import { IoSearch } from "react-icons/io5";
import Input from "./Input";
import Row from "./Row";
import Button from "./Button";
import AddInventory from "../features/inventory/AddInventory";

function TableActions() {
  const [query, setQuery] = useState("");
  return (
    <div className="rounded-t-xl border border-gray-300 bg-white p-4">
      <Row type="horizontal">
        <Input
          type="email"
          placeholder="Name"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          required
          icon={<IoSearch size={18} />}
          className="max-w-[400px] flex-1"
        />

        <Row type="horizontal">
          <AddInventory />
          <Button
            type="submit"
            variant="primary"
            size="md"
            textColor="text-white"
            bgColor="bg-teal-500"
            className="hover:bg-teal-600"
            rounded="rounded-xl"
            icon={<BiExport size={20} />}
            onClick={() => console.log("Button clicked")}
          >
            Export
          </Button>
        </Row>
      </Row>
    </div>
  );
}

export default TableActions;
