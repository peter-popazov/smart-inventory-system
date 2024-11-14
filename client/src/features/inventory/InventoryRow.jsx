import PropTypes from "prop-types";
import Menus from "@/ui/Menus";
import Modal from "@/ui/Modal";
import AddInventoryForm from "./AddInventoryForm";
import ConfirmDelete from "@/ui/ComfirmDelete";
import { MdDelete } from "react-icons/md";
import { MdEdit } from "react-icons/md";
import { useDeleteProduct } from "./useDeleteInventory";
import { formatCurrency } from "@/utils/utils";

function InventoryRow({ item }) {
  const { deleteProduct, isDeleting } = useDeleteProduct();
  if (isDeleting) return <div>Loading...</div>;
  console.log(item);

  return (
    <Modal>
      <td>
        <span>{item.SKU.replace("SKU", "")}</span>
      </td>
      <td>
        <span>{item.product}</span>
      </td>
      <td>
        <span>{formatCurrency(item.price)}</span>
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
        <span>{item.warehouseNames}</span>
      </td>
      <td>
        <Menus.Menu>
          <Menus.Toggle id={String(item.productId)} />
          <Menus.List id={String(item.productId)}>
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
          <Modal.Window name="delete" width="max-w-[700px] w-full">
            <ConfirmDelete
              resource={`Item ${item.SKU}`}
              onConfirm={() => deleteProduct(item.productId)}
            />
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
