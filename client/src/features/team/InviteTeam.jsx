import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  SelectContent,
  SelectItem,
  SelectValue,
  Select,
  SelectTrigger,
} from "@/Components/ui/select";
import toast from "react-hot-toast";
import { useState } from "react";
import { useAddTeam } from "./useInviteTeam";
import Button from "@/ui/button";
import Spinner from "@/ui/Spinner";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { IoIosAdd } from "react-icons/io";
import { ICONS_SIZE_SM } from "@/constants/iconSize";
import { useTeams } from "./useTeam";
import SpinnerFS from "@/ui/SpinnerFS";

function InviteTeam() {
  const { isLoading, data: teams } = useTeams();
  const [inviteEmail, setInviteEmail] = useState("");
  const [userRole, setUserRole] = useState("");
  const [teamId, setTeamId] = useState("");
  const { addTeam, isAdding } = useAddTeam();

  function handleInviteMember(e) {
    e.preventDefault();
    if (!inviteEmail || !teamId || !userRole) {
      toast.error("Please fulfill all fields");
      return;
    }
    addTeam({ userEmail: inviteEmail, role: userRole, teamId: teamId });
  }

  if (isLoading) {
    return <SpinnerFS />;
  }

  return (
    <Card>
      <form onSubmit={handleInviteMember}>
        <CardHeader>
          <CardTitle>Invite Team Members</CardTitle>
          <CardDescription>Add new members to your team</CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="invite-email">Email Address</Label>
            <Input
              id="invite-email"
              type="email"
              placeholder="Enter email address"
              value={inviteEmail}
              onChange={(e) => setInviteEmail(e.target.value)}
              required
            />
          </div>
          <div>
            <Label htmlFor="invite-team">Team</Label>
            <Select onValueChange={(value) => setTeamId(Number(value))}>
              <SelectTrigger>
                <SelectValue placeholder="Select a team" />
              </SelectTrigger>
              <SelectContent>
                {teams?.map((team) => (
                  <SelectItem key={team.teamId} value={String(team.teamId)}>
                    {team.teamName}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-2">
            <Label htmlFor="invite-email">User role</Label>
            <Input
              id="invite-user-role"
              type="text"
              placeholder="Enter user role"
              value={userRole}
              onChange={(e) => setUserRole(e.target.value)}
              required
            />
          </div>
        </CardContent>
        <CardFooter>
          <Button
            type="submit"
            bgColor="bg-violet-600"
            textColor="text-gray-50"
            rounded="rounded-xl"
            className="hover:bg-violet-700"
            icon={
              isAdding ? (
                <Spinner size={ICONS_SIZE_SM} className="text-white" />
              ) : (
                <IoIosAdd size={ICONS_SIZE_SM} />
              )
            }
          >
            Invite
          </Button>
        </CardFooter>
      </form>
    </Card>
  );
}

export default InviteTeam;
