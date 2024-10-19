import PropTypes from "prop-types";
import Button from "../../ui/Button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useForm } from "react-hook-form";
import FormRow from "../../ui/FormRow";

const categories = [
  { id: 1, name: "Category 1", value: "category1" },
  { id: 2, name: "Category 2", value: "category2" },
  { id: 3, name: "Category 3", value: "category3" },
];

const warehouses = [
  { id: 1, name: "Warehouse 1", value: "warehouse1" },
  { id: 2, name: "Warehouse 2", value: "warehouse2" },
  { id: 3, name: "Warehouse 3", value: "warehouse3" },
];

function AddInventoryForm({ onCloseModal, isAddItem }) {
  const {
    register: addItem,
    handleSubmit,
    formState: { errors },
  } = useForm();

  function onSubmit(data) {
    console.log("Form Data:", data);
  }

  function onError() {
    console.error("Form Errors:", errors);
  }

  return (
    <>
      <h1 className="mb-2 text-lg font-bold text-gray-900 md:mb-5 md:text-2xl">
        {isAddItem ? "Add new" : "Edit"} item
      </h1>
      <form
        className="space-y-4 text-gray-800"
        onSubmit={handleSubmit(onSubmit, onError)}
      >
        <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
          <div className="space-y-2">
            <FormRow label="Item name" error={errors?.productName?.message}>
              <Input
                id="productName"
                placeholder="Input text"
                {...addItem("productName", {
                  required: "Item name is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow label="Product SKU" error={errors?.productSKU?.message}>
              <Input
                id="productSKU"
                placeholder="Input text"
                {...addItem("productSKU", {
                  required: "Product SKU is required",
                })}
              />
            </FormRow>
          </div>
        </div>

        <div className="space-y-2">
          <FormRow label="Description" error={errors?.description?.message}>
            <Textarea
              id="description"
              placeholder="Input text"
              {...addItem("description", {
                required: "Description is required",
              })}
            />
          </FormRow>
        </div>

        <div className="grid grid-cols-1 gap-4 md:grid-cols-3">
          <div className="space-y-2">
            <FormRow label="Category" error={errors?.category?.message}>
              <Select>
                <SelectTrigger id="category">
                  <SelectValue placeholder="Input text" />
                </SelectTrigger>
                <SelectContent>
                  {categories.map((category) => (
                    <SelectItem key={category.id} value={category.value}>
                      {category.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow label="Warehouse" error={errors?.warehouse?.message}>
              <Select
                onValueChange={() => {
                  const currentValue = addItem("warehouse").value;
                  addItem("warehouse").onChange({
                    target: { value: currentValue },
                  });
                }}
              >
                <SelectTrigger id="warehouse">
                  <SelectValue placeholder="Input text" />
                </SelectTrigger>
                <SelectContent>
                  {warehouses.map((warehouse) => (
                    <SelectItem key={warehouse.id} value={warehouse.value}>
                      {warehouse.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow label="Provider" error={errors?.provider?.message}>
              <Select>
                <SelectTrigger id="provider" disabled={true}>
                  <SelectValue placeholder="Input text" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="provider1">Provider 1</SelectItem>
                  <SelectItem value="provider2">Provider 2</SelectItem>
                  <SelectItem value="provider3">Provider 3</SelectItem>
                </SelectContent>
              </Select>
            </FormRow>
          </div>
        </div>

        <div className="grid grid-cols-1 gap-4 md:grid-cols-3">
          <div className="space-y-2">
            <FormRow label="Price" error={errors?.price?.message}>
              <Input
                id="price"
                placeholder="Input text"
                type="number"
                {...addItem("price", {
                  required: "Price is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow
              label="Stock available"
              error={errors?.stockAvailable?.message}
            >
              <Input
                id="stockAvailable"
                placeholder="Input number"
                {...addItem("stockAvailable", {
                  required: "Stock available is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-3">
            <FormRow
              label="Min stock level"
              error={errors?.minStockLevel?.message}
            >
              <Input
                id="minStockLevel"
                placeholder="Input text"
                type="number"
                {...addItem("minStockLevel", {
                  required: "Min stock level is required",
                })}
              />
            </FormRow>
          </div>
        </div>

        <div className="flex space-x-4">
          <Button
            type="submit"
            textColor="text-white"
            bgColor="bg-violet-600"
            rounded="rounded-xl"
            className="hover:bg-violet-700"
          >
            {isAddItem ? "Add" : "Edit"}
          </Button>
          <Button
            type="reset"
            textColor="text-rose-700"
            bgColor="bg-none"
            rounded="rounded-xl"
            className="outline-offset-[0.5px] !duration-100 hover:outline"
            onClick={() => onCloseModal?.()}
          >
            Discard
          </Button>
        </div>
      </form>
    </>
  );
}

AddInventoryForm.propTypes = {
  onCloseModal: PropTypes.func,
  isAddItem: PropTypes.bool,
};

export default AddInventoryForm;
