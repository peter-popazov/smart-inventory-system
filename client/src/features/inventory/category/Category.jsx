import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState } from "react";
import { capitalize } from "@/utils/utils";
import { useCategory } from "./useCategory";
import Input from "@/ui/Input";
import FormRow from "@/ui/FormRow";
import AddCategory from "./AddCategory";
import PropTypes from "prop-types";

function Category({ errors, setValue, productToEdit, isEditItem }) {
  const [isSelectOpen, setIsSelectOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const { isLoadingCategories, data: categoriesApi } = useCategory();

  const categories = Array.isArray(categoriesApi)
    ? categoriesApi.map((category) => ({
        ...category,
        nameDisplay: capitalize(category.name),
      }))
    : [];

  const filteredCategories = categories.filter((category) =>
    category.nameDisplay.toLowerCase().includes(searchTerm.toLowerCase()),
  );

  if (isLoadingCategories) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <FormRow label="Category" error={errors?.category?.message}>
        <Select
          id="category"
          open={isSelectOpen}
          onOpenChange={setIsSelectOpen}
          defaultValue={isEditItem ? productToEdit.category : ""}
          onValueChange={(value) => setValue("category", value)}
        >
          <SelectTrigger id="category">
            <SelectValue placeholder="Select a category" />
          </SelectTrigger>
          <SelectContent>
            <div className="py-2">
              <Input
                type="text"
                rounded="rounded-lg"
                placeholder="Search categories..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="h-9 w-full"
              />
            </div>
            {filteredCategories.length > 0
              ? filteredCategories.map((category) => (
                  <SelectItem key={category.id} value={category.name}>
                    {category.nameDisplay}
                  </SelectItem>
                ))
              : ""}
          </SelectContent>
        </Select>
      </FormRow>
      <AddCategory />
    </>
  );
}

Category.propTypes = {
  errors: PropTypes.object,
  setValue: PropTypes.func.isRequired,
  productToEdit: PropTypes.object,
  isEditItem: PropTypes.bool.isRequired,
};

export default Category;
