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
import Button from "@/ui/button";
import { useState } from "react";

function InviteTeam() {
  const [inviteEmail, setInviteEmail] = useState("");
  const handleInviteMember = (e) => {
    e.preventDefault();
    // Here you would typically send an invitation email to the provided address
    // toast({
    //   title: "Invitation Sent",
    //   description: `An invitation has been sent to ${inviteEmail}.`,
    // });
    console.log(`An invitation has been sent to ${inviteEmail}.`);
    setInviteEmail("");
  };
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
        </CardContent>
        <CardFooter>
          <Button icon={<span>+</span>}>Send Invitation</Button>
        </CardFooter>
      </form>
    </Card>
  );
}

export default InviteTeam;
