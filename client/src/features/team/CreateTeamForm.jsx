import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useState } from "react";
import { Textarea } from "@/components/ui/textarea";
import Button from "@/ui/button";

function CreateTeamForm() {
  const [teamName, setTeamName] = useState("");
  const [teamDescription, setTeamDescription] = useState("");
  const handleCreateTeam = (e) => {
    e.preventDefault();
    // Here you would typically send a request to your backend to create the team
    // toast({
    //   title: "Team Created",
    //   description: `Team "${teamName}" has been created successfully.`,
    // });
    console.log(`Team "${teamName}" has been created successfully.`);
    setTeamName("");
    setTeamDescription("");
  };
  return (
    <Card>
      <form onSubmit={handleCreateTeam}>
        <CardHeader>
          <CardTitle>Create New Team</CardTitle>
          <CardDescription>
            Set up a new team for your organization
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="team-name">Team Name</Label>
            <Input
              id="team-name"
              placeholder="Enter team name"
              value={teamName}
              onChange={(e) => setTeamName(e.target.value)}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="team-description">Team Description</Label>
            <Textarea
              id="team-description"
              placeholder="Describe the team's purpose"
              value={teamDescription}
              onChange={(e) => setTeamDescription(e.target.value)}
            />
          </div>
        </CardContent>
        <CardFooter>
          <Button icon={<span>+</span>}>Create Team</Button>
        </CardFooter>
      </form>
    </Card>
  );
}

export default CreateTeamForm;
