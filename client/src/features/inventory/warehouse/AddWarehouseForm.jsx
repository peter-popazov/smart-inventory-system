import Button from "@/ui/Button";
import FormRow from "@/ui/FormRow";
import { useForm } from "react-hook-form";
import { useCreateWarehouse } from "./useCreateWarehouse";
import { Input } from "@/components/ui/input";
import { Checkbox } from "@/components/ui/checkbox";
import PropTypes from "prop-types";
import SpinnerFS from "@/ui/SpinnerFS";

function AddWarehouseForm({ onCloseModal, warehouseToEdit = {} }) {
  const { createWarehouse, isCreateing } = useCreateWarehouse();
  const isEditItem = !!Object.keys(warehouseToEdit).length;
  const editValues = { ...warehouseToEdit };

  const {
    register,
    setValue,
    getValues,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: isEditItem ? editValues : {},
  });

  function onSubmit(data, e) {
    e.preventDefault();
    createWarehouse(data);
    onCloseModal?.();
  }

  function onError(error, e) {
    console.log(e);
    e.preventDefault();
    console.log(error);
  }

  function handleButtonSumbit(e) {
    handleSubmit(onSubmit, onError)(e);
  }

  if (isCreateing) {
    return <SpinnerFS />;
  }

  return (
    <>
      <h4 className="text-md mb-4 font-semibold text-gray-900 md:text-lg">
        Add warehouse
      </h4>
      <form
        className="space-y-4 text-gray-800"
      >
        <div className="grid gap-4">
          <FormRow label="Warehouse name" error={errors?.name?.message}>
            <Input
              id="name"
              placeholder="Input text"
              className="w-full"
              {...register("name", {
                required: "Warehouse name is required",
              })}
            />
          </FormRow>

          <FormRow label="Address" error={errors?.address?.message}>
            <Input
              id="address"
              placeholder="Input text"
              {...register("address", {
                required: "Address is required",
              })}
            />
          </FormRow>

          <FormRow
            label="isRefrigerated"
            mb="mb-0"
            error={errors?.isRefrigerated?.message}
            className="flex flex-row gap-3"
          >
            <Checkbox
              id="isRefrigerated"
              checked={getValues("isRefrigerated")}
              onCheckedChange={(value) => setValue("isRefrigerated", value)}
              className="self-center"
            />
          </FormRow>
        </div>

        <div className="grid gap-4">
          <FormRow label="City" error={errors?.city?.message}>
            <Input
              id="city"
              placeholder="Input text"
              {...register("city", {
                required: "City is required",
              })}
            />
          </FormRow>

          <FormRow label="Postal Code" error={errors?.postalCode?.message}>
            <Input
              id="postalCode"
              type="number"
              placeholder="Input number"
              {...register("postalCode", {
                required: "Postal code is required",
              })}
            />
          </FormRow>
        </div>

        <div className="flex space-x-4">
          <Button
            onClick={handleButtonSumbit}
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
            onClick={onCloseModal}
          >
            Discard
          </Button>
        </div>
      </form>
    </>
  );
}

AddWarehouseForm.propTypes = {
  onCloseModal: PropTypes.func,
  warehouseToEdit: PropTypes.object,
};

export default AddWarehouseForm;
