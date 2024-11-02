import PropTypes from "prop-types";
import { BiExport } from "react-icons/bi";
import { IoSearch } from "react-icons/io5";
import Input from "./Input";
import Row from "./Row";
import Button from "./Button";
import AddInventory from "../features/inventory/AddInventory";
import { useQueryClient } from "react-query";
import { exportToJSON } from "../utils/exportToCSV";

function TableActions({ query, setQuery }) {
  const queryClient = useQueryClient();
  const products = queryClient.getQueryData("inventory");

  return (
    <div className="rounded-t-xl border border-gray-300 bg-white p-3">
      <Row type="horizontal">
        <Input
          type="email"
          placeholder="Name"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          required
          icon={<IoSearch size={18} />}
          className="h-8 max-w-[300px] flex-1 text-sm"
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
            onClick={() => exportToJSON(products, "inventory.json")}
          >
            Export
          </Button>
        </Row>
      </Row>
    </div>
  );
}

TableActions.propTypes = {
  query: PropTypes.string.isRequired,
  setQuery: PropTypes.func.isRequired,
};

export default TableActions;
