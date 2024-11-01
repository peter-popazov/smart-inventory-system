import { useState } from "react";
import { useForm } from "react-hook-form";
import Button from "../../ui/Button";
import FormRow from "../../ui/FormRow";
import Input from "../../ui/Input";
import { MdEmail } from "react-icons/md";
import { RiLockPasswordFill } from "react-icons/ri";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { useRegister } from "./useRegister";

function RegisterForm() {
  const [passwordVisible, setPasswordVisible] = useState(true);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const { registerApi, isLoading } = useRegister();

  function onSubmit(data) {
    registerApi({ ...data, role: "USER" });
  }

  function onError(errors) {
    console.error("Form Errors:", errors);
  }

  return (
    <form
      onSubmit={handleSubmit(onSubmit, onError)}
      className="mt-8 flex flex-col gap-2 md:mt-12"
    >
      <FormRow label="Email" error={errors?.email?.message}>
        <Input
          type="email"
          id="email"
          placeholder="example@example.com"
          height="h-10"
          className="w-full"
          autoComplete="username"
          disabled={isLoading}
          icon={<MdEmail />}
          useFormHook={{
            ...register("email", { required: "Email is required" }),
          }}
        />
      </FormRow>

      <FormRow label="Password" error={errors?.password?.message}>
        <Input
          type={!passwordVisible ? "text" : "password"}
          id="password"
          placeholder="**********"
          height="h-10"
          className="w-full"
          autoComplete="current-password"
          disabled={isLoading}
          icon={<RiLockPasswordFill />}
          useFormHook={{
            ...register("password", {
              required: "Password is required",
            }),
          }}
        >
          <button
            type="button"
            onClick={() => setPasswordVisible(!passwordVisible)}
          >
            {passwordVisible ? <FaEyeSlash /> : <FaEye />}
          </button>
        </Input>
      </FormRow>

      <div className="mt-6 w-full">
        <Button
          className="h-10 w-full hover:bg-violet-800"
          rounded="rounded-xl"
          bgColor="bg-violet-600"
          textColor="text-white"
          type="submit"
        >
          {isLoading ? "Loading..." : "Register"}
        </Button>
      </div>
    </form>
  );
}

export default RegisterForm;
