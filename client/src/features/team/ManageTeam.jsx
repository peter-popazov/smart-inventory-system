import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Button from "@/ui/button";
import { useState } from "react";
import { Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import Table from "@/ui/Table";

const headers = ["Name", "Email", "Role", "Actions"];

function ManageTeam() {
  const initialTeamMembers = [
    {
      id: "1",
      name: "Alice Johnson",
      email: "alice@example.com",
      role: "Admin",
    },
    { id: "2", name: "Bob Smith", email: "bob@example.com", role: "Member" },
    {
      id: "3",
      name: "Charlie Brown",
      email: "charlie@example.com",
      role: "Member",
    },
  ];

  const [teamMembers] = useState(initialTeamMembers);
  const [searchTerm, setSearchTerm] = useState("");

  const filteredMembers = teamMembers.filter(
    (member) =>
      member.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      member.email.toLowerCase().includes(searchTerm.toLowerCase()),
  );

  return (
    <Card>
      <CardHeader>
        <CardTitle>Team Members</CardTitle>
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
              <th key={header} className="pl-4 text-left">
                {header}
              </th>
            )}
          />
          <Table.Body
            data={filteredMembers}
            render={(member) => (
              <>
                <td className="p-4">{member.name}</td>
                <td className="p-4">{member.email}</td>
                <td className="p-4">{member.role}</td>
                <td className="p-4">
                  <Button variant="outline" size="sm">
                    Edit
                  </Button>
                </td>
              </>
            )}
          />
        </Table>
      </CardContent>
    </Card>
  );
}

export default ManageTeam;
