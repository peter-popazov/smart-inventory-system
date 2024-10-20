import PropTypes from "prop-types";
import Menus from "@/ui/Menus";
import { MdEdit } from "react-icons/md";
import { MdDelete } from "react-icons/md";
import Modal from "@/ui/Modal";
import AddInventoryForm from "./AddInventoryForm";

function InventoryRow({ item }) {
  return (
    <Modal>
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
        <span>${item.price}</span>
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
      <td>
        <Menus.Menu>
          <Menus.Toggle id={item.SKU} />
          <Menus.List id={item.SKU}>
            <Modal.Open opens="edit">
              <Menus.Button icon={<MdEdit size={16} />}>Edit</Menus.Button>
            </Modal.Open>

            <Modal.Open opens="delete">
              <Menus.Button icon={<MdDelete size={16} />}>Delete</Menus.Button>
            </Modal.Open>
          </Menus.List>

          <Modal.Window name="edit">
            <AddInventoryForm productToEdit={item} />
          </Modal.Window>
          <Modal.Window name="delete">
            <div>Delete window content goes here.</div>
          </Modal.Window>
        </Menus.Menu>
      </td>
    </Modal>
  );
}

InventoryRow.propTypes = {
  item: PropTypes.object.isRequired,
};

export default InventoryRow;
