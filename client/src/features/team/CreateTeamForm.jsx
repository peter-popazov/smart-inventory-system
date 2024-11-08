import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import Button from "@/ui/Button";
import FormRow from "@/ui/FormRow";
import { useForm } from "react-hook-form";
import { IoIosAdd } from "react-icons/io";
import { ICONS_SIZE_SM } from "@/constants/iconSize";
import { useCreateTeam } from "./useCreateTeam";
import SpinnerFS from "@/ui/SpinnerFS";

function CreateTeamForm() {
  const { createTeam, isCreating } = useCreateTeam();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  function onSubmit(data) {
    console.log(data);
    createTeam(data);
  }

  function onError(errors) {
    console.log(errors);
  }

  if (isCreating) {
    return <SpinnerFS />;
  }

  return (
    <Card>
      <form onSubmit={handleSubmit(onSubmit, onError)}>
        <CardHeader>
          <CardTitle>Create New Team</CardTitle>
          <CardDescription>
            Set up a new team for your organization
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <FormRow label="Team Name" error={errors?.teamName?.message}>
              <Input
                id="teamName"
                placeholder="Input text"
                {...register("teamName", {
                  required: "Team name is required",
                })}
              />
            </FormRow>
          </div>
          <div className="space-y-2">
            <FormRow
              label="Team description"
              error={errors?.teamDescription?.message}
            >
              <Textarea
                id="teamDescription"
                placeholder="Input text"
                {...register("teamDescription", {
                  required: "Team description is required",
                })}
              />
            </FormRow>
          </div>
        </CardContent>
        <CardFooter>
          <Button
            type="submit"
            bgColor="bg-violet-600"
            textColor="text-gray-50"
            rounded="rounded-xl"
            className="hover:bg-violet-700"
            icon={<IoIosAdd size={ICONS_SIZE_SM} />}
          >
            Create Team
          </Button>
        </CardFooter>
      </form>
    </Card>
  );
}

export default CreateTeamForm;
