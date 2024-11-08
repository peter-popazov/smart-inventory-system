import FormRow from "@/ui/FormRow";
import { Input } from "@/components/ui/input";
import Button from "@/ui/Button";
import { useForm } from "react-hook-form";
import { useUpdateUser } from "./useUpdateUser";
import SpinnerFS from "@/ui/SpinnerFS";
import { useState } from "react";

function AddUserInfo() {
  const [buttonClicked, setButtonClicked] = useState(0);
  const { updateUser, isUpdating } = useUpdateUser();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  function onSubmit(data) {
    console.log(data);
    updateUser(data);
    setButtonClicked((prev) => prev + 1);
  }

  function onError(errors) {
    console.log(errors);
  }

  if (isUpdating) {
    return <SpinnerFS />;
  }
  console.log(buttonClicked);

  if (buttonClicked >= 1) {
    return <div className="text-sm font-medium">Submitted</div>;
  }

  return (
    <form onSubmit={handleSubmit(onSubmit, onError)}>
      <div className="space-y-2 p-2 text-sm">
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
        className="mt-4 h-9 w-full"
        rounded="rounded-lg"
      >
        Submit
      </Button>
    </form>
  );
}

export default AddUserInfo;
