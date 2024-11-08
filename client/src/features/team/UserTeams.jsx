import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import Table from "@/ui/Table";
import { useState } from "react";
import TeamRow from "./TeamRow";
import { useTeams } from "./useTeam";
import SpinnerFS from "@/ui/SpinnerFS";

const headers = ["Name", "Members", "Description", "Action"];

function UserTeams() {
  const { isLoading, data: teams } = useTeams();
  const [teamSearchTerm, setTeamSearchTerm] = useState("");
  const filteredTeams = teams
    ? teams.filter(
        (team) =>
          team.teamName.toLowerCase().includes(teamSearchTerm.toLowerCase()) ||
          team.teamDescription
            .toLowerCase()
            .includes(teamSearchTerm.toLowerCase()),
      )
    : [];

  if (isLoading) return <SpinnerFS />;

  return (
    <Card>
      <CardHeader>
        <CardTitle>My Teams</CardTitle>
        <CardDescription>View and manage your teams</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="mb-4 flex items-center">
          <Input
            placeholder="Search teams"
            value={teamSearchTerm}
            onChange={(e) => setTeamSearchTerm(e.target.value)}
            className="max-w-sm"
          />
        </div>
        <Table cols="grid-cols-4">
          <Table.Header
            data={headers}
            render={(header) => (
              <th className="text-left" key={header}>
                {header}
              </th>
            )}
          />
          <Table.Body
            data={filteredTeams}
            render={(team, index) => <TeamRow key={index} team={team} />}
          />
        </Table>
      </CardContent>
    </Card>
  );
}

export default UserTeams;
