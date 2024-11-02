import Button from "@/ui/Button";
import Input from "@/ui/Input";
import PropTypes from "prop-types";
import { useState } from "react";
import toast from "react-hot-toast";
import Row from "@/ui/Row";
import { useCreateCategory } from "./useCreateCategoty";
import SpinnerFS from "@/ui/SpinnerFS";

function AddCategoryForm({ onCloseModal }) {
  const [newCategory, setNewCategory] = useState("");
  const { createCategory, isCreateing } = useCreateCategory();

  function handleAddCategory(e) {
    e.preventDefault();
    if (newCategory.trim()) {
      setNewCategory("");
      createCategory(newCategory);
    } else {
      toast.error("Please enter a category name.");
    }
    onCloseModal?.();
  }

  if (isCreateing) {
    return <SpinnerFS />;
  }

  return (
    <>
      <h4 className="text-md mb-4 font-semibold md:text-xl">Add Category</h4>
      <form>
        <Row type="horizontal">
          <Input
            type="text"
            placeholder="New category name"
            rounded="rounded-lg"
            className="h-10 max-w-64 grow rounded border p-2 text-sm"
            value={newCategory}
            onChange={(e) => setNewCategory(e.target.value)}
          />

          <Button
            type="submit"
            size="sm"
            textColor="text-white"
            bgColor="bg-violet-500"
            className="h-8 max-w-32 grow hover:bg-violet-700"
            rounded="rounded-lg"
            onClick={(e) => handleAddCategory(e)}
          >
            Add
          </Button>
        </Row>
      </form>
    </>
  );
}

AddCategoryForm.propTypes = {
  onCloseModal: PropTypes.func,
};

export default AddCategoryForm;
