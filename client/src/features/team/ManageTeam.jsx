import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useState } from "react";
import { Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import Table from "@/ui/Table";
import ManageTeamRow from "./ManageTeamRow";
import PropTypes from "prop-types";
import { filterMembers } from "@/utils/utils";
import { useQueryClient } from "react-query";

const headers = ["Name", "Email", "Role", "Actions"];

function ManageTeam({ team }) {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  const [searchTerm, setSearchTerm] = useState("");

  const teamMembers = team.members;
  const filteredMembers = filterMembers(teamMembers, searchTerm);

  return (
    <Card>
      <CardHeader>
        <CardTitle>{team.teamName} Members</CardTitle>
        <CardDescription>Manage your current team members</CardDescription>
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
        <Table cols="grid-cols-4">
          <Table.Header
            data={headers}
            render={(header) => (
              <th key={header} className="text-left">
                {header}
              </th>
            )}
          />
          <Table.Body
            data={filteredMembers}
            hilightRow={(member) => user?.email === member.email}
            render={(member) => (
              <ManageTeamRow
                key={member.userId}
                member={member}
                user={user}
                teamId={team.teamId}
              />
            )}
          />
        </Table>
      </CardContent>
    </Card>
  );
}

ManageTeam.propTypes = {
  team: PropTypes.object.isRequired,
};

export default ManageTeam;
