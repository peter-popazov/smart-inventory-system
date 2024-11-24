import { LuUsers } from "react-icons/lu";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

import PageHeader from "@/ui/PageHeader";
import CreateTeamForm from "@/features/team/CreateTeamForm";
import InviteTeam from "@/features/team/InviteTeam";
import UserTeams from "@/features/team/UserTeams";
import { useQueryClient } from "react-query";
import MyTeam from "@/features/team/MyTeam";

export default function Team() {
  const queryClient = useQueryClient();
  const user = queryClient.getQueryData("user");
  return (
    <div className="flex min-h-screen flex-col">
      <PageHeader icon={<LuUsers size={24} />}>Team Management</PageHeader>
      <div className="flex-1 space-y-6 p-4 md:p-6">
        {user?.role === "ADMIN" || user?.role === "USER" ? (
          <Tabs defaultValue="userTeams">
            <TabsList>
              <TabsTrigger value="userTeams">My teams</TabsTrigger>
              <TabsTrigger value="create">Create Team</TabsTrigger>
              <TabsTrigger value="invite">Invite Members</TabsTrigger>
            </TabsList>
            <TabsContent value="userTeams">
              <UserTeams />
            </TabsContent>
            <TabsContent value="create">
              <CreateTeamForm />
            </TabsContent>
            <TabsContent value="invite">
              <InviteTeam />
            </TabsContent>
          </Tabs>
        ) : (
          <MyTeam />
        )}
      </div>
    </div>
  );
}
