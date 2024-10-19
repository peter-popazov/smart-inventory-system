import PropTypes from "prop-types";
import Table from "../../ui/Table";

function InventoryRow({ item }) {
  return (
    <Table.Row>
      <td>
        <span>{item.SKU.replace("SKU", "")}</span>
      </td>
      <td className="flex items-center">
        <div className="mr-4 hidden h-16 items-center md:flex">
          <img src="https://via.placeholder.com/50" alt={item.product} />
        </div>
        <span>{item.product}</span>
      </td>
      <td>
        <span></span>${item.price}
      </td>
      <td>
        <span>{item.quantity}</span>
      </td>
      <td>
        <span>{item.category}</span>
      </td>
      <td>
        <span>{item.reorderLevel}</span>
      </td>
      <td>
        <span>{item.provider}</span>
      </td>
      <td>;</td>
    </Table.Row>
  );
}

InventoryRow.propTypes = {
  item: PropTypes.object.isRequired,
};

export default InventoryRow;
