import Button from "./Button";
import { LuDownload } from "react-icons/lu";
import { BiExport } from "react-icons/bi";
import Input from "./Input";
import { useState } from "react";
import Row from "./Row";
import { IoSearch } from "react-icons/io5";

function TableActions() {
  const [query, setQuery] = useState("");
  return (
    <div className="rounded-t-xl border border-gray-300 bg-white p-4">
      <Row type="horizontal">
        <Input
          type="email"
          placeholder="Search..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          required
          icon={<IoSearch size={18} />}
        />

        <Row type="horizontal">
          <Button
            type="submit"
            variant="primary"
            size="md"
            textColor="text-white"
            bgColor="bg-teal-400"
            rounded="rounded-xl"
            icon={<BiExport size={20} />}
            onClick={() => console.log("Button clicked")}
          >
            Export
          </Button>

          <Button
            type="submit"
            variant="primary"
            size="md"
            textColor="text-white"
            bgColor="bg-violet-500"
            rounded="rounded-xl"
            icon={<LuDownload size={20} />}
            onClick={() => console.log("Button clicked")}
          >
            Import
          </Button>
        </Row>
      </Row>
    </div>
  );
}

export default TableActions;
