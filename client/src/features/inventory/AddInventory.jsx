import Button from "../../ui/Button";
import Modal from "../../ui/Modal";
import AddInventoryForm from "./AddInventoryForm";
import { IoAddSharp } from "react-icons/io5";

function AddInventory() {
  return (
    <Modal>
      <Modal.Open opens="product-form">
        <Button
          type="submit"
          variant="primary"
          size="md"
          textColor="text-white"
          bgColor="bg-violet-500"
          className="hover:bg-violet-700"
          rounded="rounded-xl"
          icon={<IoAddSharp size={20} />}
        >
          Add Item
        </Button>
      </Modal.Open>
      <Modal.Window name="product-form">
        <AddInventoryForm isAddItem={true} />
      </Modal.Window>
    </Modal>
  );
}

export default AddInventory;
