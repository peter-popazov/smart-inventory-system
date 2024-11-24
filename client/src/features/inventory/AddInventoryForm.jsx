import PropTypes from "prop-types";
import Button from "../../ui/Button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
// import {
//   Select,
//   SelectContent,
//   SelectItem,
//   SelectTrigger,
//   SelectValue,
// } from "@/components/ui/select";
import { useForm } from "react-hook-form";
import { useCreateProduct } from "./useCreateProduct";
import FormRow from "../../ui/FormRow";
import Warehouse from "./warehouse/Warehouse";
import Category from "./category/Category";
import { useState } from "react";
import { useUpdateProduct } from "./useUpdateProduct";
import SpinnerFS from "@/ui/SpinnerFS";

function AddInventoryForm({ onCloseModal, productToEdit = {} }) {
  const [totalStock, setTotalStock] = useState(0);
  const { createProduct, isCreateing } = useCreateProduct();
  const { updateProduct, isUpdating } = useUpdateProduct();

  const { productId } = productToEdit;
  const isEditItem = Boolean(productId);
  const editValues = {
    productId: productToEdit.productId,
    productSKU: productToEdit.SKU,
    barCode: productToEdit.barCode,
    productName: productToEdit.product,
    price: productToEdit.price,
    stockAvailable: productToEdit?.inventories?.at(0)?.stockAvailable,
    minStockLevel: productToEdit.reorderLevel,
    maxStockLevel: productToEdit.maxStockLevel,
    description: productToEdit.description,
    category: productToEdit.category,
    warehouse: productToEdit?.inventories?.at(0)?.warehouse?.warehouseId,
    provider: productToEdit.provider,
    inventories: productToEdit.inventories,
    quantity: productToEdit?.inventories?.reduce((total, inventory) => {
      return total + inventory.stockAvailable;
    }, 0),
  };

  const {
    register,
    setValue,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: isEditItem ? editValues : {},
  });

  function onSubmit(data) {
    if (!isEditItem) {
      createProduct({
        barcode: data.barCode,
        productCode: data.productSKU,
        productName: data.productName,
        description: data.description,
        price: parseFloat(data.price),
        categoryName: data.category,
        minStockLevel: data.minStockLevel,
        maxStockLevel: data.maxStockLevel,
        quantityAvailable: parseInt(data.stockAvailable),
        warehouseId: data.warehouse,
        //
        weight: 0.1,
        height: 0.1,
        depth: 0.1,
        width: 0.1,
        //
      });
    } else {
      let inventoryToUpdate = productToEdit.inventories
        .filter(
          (inventory) => inventory.warehouse.warehouseId === data.warehouse,
        )
        .map((inventory) => inventory.inventoryId)
        ?.at(0);
      const obj = {
        inventoryId: inventoryToUpdate,
        productId: data.productId,
        productCode: data.productSKU,
        barcode: parseInt(data.barCode),
        productName: data.productName,
        description: data.description,
        price: parseFloat(data.price),
        categoryName: data.category,
        minStockLevel: data.minStockLevel,
        maxStockLevel: data.maxStockLevel,
        quantityAvailable: parseInt(data.stockAvailable),
        warehouseId: data.warehouse,
        //
        weight: 0.1,
        height: 0.1,
        depth: 0.1,
        width: 0.1,
        //
      };
      console.log(obj);

      updateProduct(obj);
    }
    onCloseModal?.();
  }

  function onError() {
    console.error("Form Errors:", errors);
  }

  if (isCreateing || isUpdating) {
    return <SpinnerFS />;
  }

  return (
    <>
      <h1 className="mb-2 text-lg font-bold text-gray-900 md:mb-5 md:text-2xl">
        {isEditItem ? "Edit" : "Add new"} item
      </h1>
      <form
        className="space-y-4 text-gray-800"
        onSubmit={handleSubmit(onSubmit, onError)}
      >
        <div className="grid grid-cols-1 gap-4 md:grid-cols-4">
          <div className="space-y-2">
            <FormRow label="Item name" error={errors?.productName?.message}>
              <Input
                id="productName"
                placeholder="Input text"
                {...register("productName", {
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
                {...register("productSKU", {
                  required: "Product SKU is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-2">
            {/* <div className="absolute top-3 right-3">
              <BsUpcScan size={ICONS_SIZE - 6} />
            </div> */}
            <FormRow label="Barcode" error={errors?.barCode?.message}>
              <Input
                id="barCode"
                placeholder="Input text"
                {...register("barCode", {
                  required: "Bar Code is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow label="Price" error={errors?.price?.message}>
              <Input
                id="price"
                type="number"
                step="0.01"
                placeholder="Input text"
                {...register("price", {
                  required: "Price is required",
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
              {...register("description", {
                required: "Description is required",
              })}
            />
          </FormRow>
        </div>

        <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
          <div className="space-y-2">
            <Warehouse
              errors={errors}
              setValue={setValue}
              productToEdit={productToEdit}
              isEditItem={isEditItem}
            />
          </div>
          <div className="space-y-2">
            <Category
              errors={errors}
              setValue={setValue}
              productToEdit={productToEdit}
              isEditItem={isEditItem}
            />
          </div>

          {/* <FormRow label="Provider" error={errors?.provider?.message}>
            <Select
              defaultValue={isEditItem ? productToEdit.provider : ""}
              onValueChange={(value) => setValue("provider", value)}
            >
              <SelectTrigger id="provider">
                <SelectValue placeholder="Select a provider" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="provider1">Provider 1</SelectItem>
              </SelectContent>
            </Select>
          </FormRow> */}
        </div>

        <div className="grid grid-cols-1 gap-4 md:grid-cols-4">
          <div className="space-y-2">
            <FormRow
              label="Warehouse stock"
              error={errors?.stockAvailable?.message}
            >
              <Input
                id="stockAvailable"
                type="number"
                placeholder="Input number"
                {...register("stockAvailable", {
                  required: "Stock available is required",
                })}
                onChange={(e) => {
                  setTotalStock(e.target.value);
                }}
              />
            </FormRow>
          </div>
          <div className="space-y-3">
            <FormRow label="Total Stock" error={errors?.totalStock?.message}>
              <div
                id="totalStock"
                className="border-1 flex h-9 items-center rounded-md border p-1 shadow-sm"
              >
                <span className="ml-2 text-sm">
                  {isEditItem ? (
                    productToEdit.quantity
                  ) : (
                    <span
                      className={`${totalStock > 0 ? "text-gray-800" : "text-gray-500"}`}
                    >
                      {totalStock > 0 ? totalStock : "Warehouse stock"}
                    </span>
                  )}
                </span>
              </div>
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow
              label="Min stock level"
              error={errors?.minStockLevel?.message}
            >
              <Input
                id="minStockLevel"
                placeholder="Input text"
                type="number"
                {...register("minStockLevel", {
                  required: "Min stock level is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow
              label="Max stock level"
              error={errors?.maxStockLevel?.message}
            >
              <Input
                id="maxStockLevel"
                placeholder="Input text"
                type="number"
                {...register("maxStockLevel", {
                  required: "Max stock level is required",
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
            {isEditItem ? "Edit" : "Add"}
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
  productToEdit: PropTypes.object,
  isAddItem: PropTypes.bool,
};

export default AddInventoryForm;
