import Modal from "@/ui/Modal";
import Button from "@/ui/Button";
import { IoAddSharp } from "react-icons/io5";
import AddCategoryForm from "./AddCategoryForm";

function AddCategory() {
  return (
    <div className="my-2">
      <Modal>
        <Modal.Open opens="category-add">
          <Button
            type="button"
            size="sm"
            rounded="rounded-lg"
            textColor="text-white"
            bgColor="bg-violet-500"
            className="full h-8 w-full hover:bg-violet-700"
            hideText={false}
            icon={<IoAddSharp size={20} />}
          >
            Add Category
          </Button>
        </Modal.Open>
        <Modal.Window name="category-add" width="w-[400px]">
          <AddCategoryForm />
        </Modal.Window>
      </Modal>
    </div>
  );
}

export default AddCategory;
