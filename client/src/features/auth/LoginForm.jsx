import { useState } from "react";
import { useForm } from "react-hook-form";
import Button from "../../ui/Button";
import FormRow from "../../ui/FormRow";
import Input from "../../ui/Input";
import { MdEmail } from "react-icons/md";
import { RiLockPasswordFill } from "react-icons/ri";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { useLogin } from "./useLogin";
import Spinner from "@/ui/Spinner";

function LoginForm() {
  const [passwordVisible, setPasswordVisible] = useState(true);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const { login, isLoading } = useLogin();

  function onSubmit(data) {
    login(data);
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
          icon={<MdEmail />}
          disabled={isLoading}
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
          icon={<RiLockPasswordFill />}
          disabled={isLoading}
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
          hideText={false}
          icon={isLoading ? <Spinner className="text-white" /> : <></>}
        >
          {isLoading ? "Loading..." : "Login"}
        </Button>
      </div>
    </form>
  );
}

export default LoginForm;
