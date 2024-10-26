import { LuUsers } from "react-icons/lu";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

import PageHeader from "@/ui/PageHeader";
import CreateTeamForm from "@/features/team/CreateTeamForm";
import InviteTeam from "@/features/team/InviteTeam";
import ManageTeam from "@/features/team/ManageTeam";

export default function Team() {
  return (
    <div className="flex min-h-screen flex-col">
      <PageHeader icon={<LuUsers size={24} />}>Team Management</PageHeader>
      <div className="flex-1 space-y-6 p-4 md:p-6">
        <Tabs defaultValue="create">
          <TabsList>
            <TabsTrigger value="create">Create Team</TabsTrigger>
            <TabsTrigger value="invite">Invite Members</TabsTrigger>
            <TabsTrigger value="manage">Manage Team</TabsTrigger>
          </TabsList>
          <TabsContent value="create">
            <CreateTeamForm />
          </TabsContent>
          <TabsContent value="invite">
            <InviteTeam />
          </TabsContent>
          <TabsContent value="manage">
            <ManageTeam />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
}
