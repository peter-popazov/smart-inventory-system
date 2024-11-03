import FormRow from "@/ui/FormRow";
import { Input } from "@/components/ui/input";
import Button from "@/ui/Button";
import { useForm } from "react-hook-form";
import { useUpdateUser } from "./useUpdateUser";
import SpinnerFS from "@/ui/SpinnerFS";

function AddUserInfo() {
  const { updateUser, isUpdating } = useUpdateUser();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  function onSubmit(data) {
    console.log(data);
    updateUser(data);
  }

  function onError(errors) {
    console.log(errors);
  }

  if (isUpdating) {
    return <SpinnerFS />
  }

  return (
    <form onSubmit={handleSubmit(onSubmit, onError)}>
      <div className="space-y-2">
        <FormRow label="Your firstname" error={errors?.firstName?.message}>
          <Input
            id="firstName"
            placeholder="Input text"
            {...register("firstName", {
              required: "Item name is required",
            })}
          />
        </FormRow>
        <FormRow label="Your lastname" error={errors?.lastName?.message}>
          <Input
            id="lastName"
            placeholder="Input text"
            {...register("lastName", {
              required: "Item name is required",
            })}
          />
        </FormRow>
      </div>
      <Button
        type="submit"
        size="sm"
        textColor="text-gray-50"
        bgColor="bg-violet-600"
        className="mt-4 w-full"
        rounded="rounded-lg"
      >
        Submit
      </Button>
    </form>
  );
}

export default AddUserInfo;
