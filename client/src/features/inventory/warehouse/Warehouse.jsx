import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useWarehouse } from "./useWarehouse";
import toast from "react-hot-toast";
import FormRow from "@/ui/FormRow";
import PropTypes from "prop-types";
import AddWarehouse from "./AddWarehouse";

function Warehouse({ errors, setValue, productToEdit, isEditItem }) {
  const {
    isLoading: isLoadingWarehouses,
    error: warehouseError,
    data: warehousesApi,
  } = useWarehouse();

  const warehouses = Array.isArray(warehousesApi)
    ? warehousesApi.map((warehouse) => ({
        ...warehouse,
        location: `${warehouse.location.address}, ${warehouse.location.city}, ${warehouse.location.postalCode}`,
      }))
    : [];

  function handleWarehouseChange(value) {
    let selectedInventory;
    if (productToEdit?.inventories) {
      selectedInventory = productToEdit.inventories.find(
        (inv) => inv.warehouse.warehouseId.toString() === value,
      );
    } else {
      selectedInventory = warehouses.find(
        (warehouse) => warehouse.warehouseId.toString() === value,
      );
    }
    setValue("warehouse", value);
    setValue(
      "stockAvailable",
      selectedInventory ? selectedInventory.stockAvailable : 0,
    );
  }

  if (warehouseError) {
    toast.error(warehouseError);
  }

  if (isLoadingWarehouses) {
    return <div>Loading...</div>;
  }

  return (
    <>
    <FormRow label="Warehouse" error={errors?.warehouse?.message}>
      <Select
        onValueChange={(value) => {
          handleWarehouseChange(value);
        }}
        defaultValue={productToEdit?.inventories
          ?.at(0)
          ?.warehouse?.warehouseId.toString()}
      >
        <SelectTrigger id="warehouse">
          <SelectValue placeholder="Select warehouse" />
        </SelectTrigger>
        <SelectContent>
          {isEditItem
            ? productToEdit.inventories.map((inventory) => (
                <SelectItem
                  key={inventory.warehouse.warehouseId}
                  value={inventory.warehouse.warehouseId.toString()}
                >
                  {inventory.warehouse.name}
                </SelectItem>
              ))
            : warehouses.map((warehouse) => (
                <SelectItem
                  key={warehouse.warehouseId}
                  value={warehouse.warehouseId.toString()}
                >
                  {warehouse.name}
                </SelectItem>
              ))}
        </SelectContent>
      </Select>
    </FormRow>
    <AddWarehouse />
    </>
  );
}

Warehouse.propTypes = {
  errors: PropTypes.object,
  setValue: PropTypes.func.isRequired,
  productToEdit: PropTypes.object,
  isEditItem: PropTypes.bool.isRequired,
};

export default Warehouse;
