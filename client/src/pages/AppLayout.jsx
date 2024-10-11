import SideBar from "../ui/SideBar";
import Main from "../ui/Main";
import Container from "../ui/Container";
import { Outlet } from "react-router-dom";

function AppLayout() {
  return (
    <div className="grid h-screen grid-cols-[19rem_1fr] grid-rows-[auto_1fr] text-gray-800">
      <SideBar />
      <Main>
        <Container>
          <Outlet />
        </Container>
      </Main>
    </div>
  );
}

export default AppLayout;
