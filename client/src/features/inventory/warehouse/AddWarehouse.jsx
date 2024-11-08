import Modal from "@/ui/Modal";
import Button from "@/ui/Button";
import { IoAddSharp } from "react-icons/io5";
import AddWarehouseForm from "./AddWarehouseForm";
import { ICONS_SIZE_XS } from "@/constants/iconSize";

function AddWarehouse() {
  return (
    <div className="my-2">
      <Modal>
        <Modal.Open opens="warehouse-add">
          <Button
            type="button"
            size="sm"
            rounded="rounded-lg"
            textColor="text-white"
            bgColor="bg-violet-500"
            className="full h-7 w-full text-xs hover:bg-violet-700"
            hideText={false}
            icon={<IoAddSharp size={ICONS_SIZE_XS} />}
          >
            Add Warehouse
          </Button>
        </Modal.Open>
        <Modal.Window name="warehouse-add">
          <AddWarehouseForm />
        </Modal.Window>
      </Modal>
    </div>
  );
}

export default AddWarehouse;
