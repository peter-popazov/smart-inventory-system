import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useEffect, useState } from "react";
import { Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import Table from "@/ui/Table";
import Spinner from "@/ui/Spinner";
import { useUserTeams } from "./useUserTeams";
import { useQueryClient } from "react-query";
import { filterMembers, getFullName } from "@/utils/utils";

const headers = ["Name", "Email", "Role"];

function MyTeam() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");

  const { data: initialTeam, isLoading } = useUserTeams();
  const team = initialTeam?.at(0);

  const [teamMembers, setTeamMembers] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    if (team && team.members) {
      setTeamMembers(team.members);
    }
  }, [team]);

  const filteredMembers = filterMembers(teamMembers, searchTerm);

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>{team?.teamName || "Team"}</CardTitle>
        <CardDescription>
          {team?.teamDescription || "Description not available"}
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="mb-4 flex items-center">
          <Search className="mr-2 h-4 w-4 opacity-50" />
          <Input
            placeholder="Search members"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="max-w-sm"
          />
        </div>
        <Table cols="grid-cols-3">
          <Table.Header
            data={headers}
            render={(header) => (
              <th key={header} className="pl-4 text-left">
                {header}
              </th>
            )}
          />
          <Table.Body
            data={filteredMembers}
            hilightRow={(member) => user?.email === member.email}
            render={(member) => (
              <>
                <td>
                  {`${member.firstName || member.lastName ? getFullName(member) : "No name"}`}
                </td>
                <td>{member.email}</td>
                <td>{member.role}</td>
              </>
            )}
          />
        </Table>
      </CardContent>
    </Card>
  );
}

export default MyTeam;
