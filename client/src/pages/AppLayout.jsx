import Header from "../ui/Header";
import SideBar from "../ui/SideBar";
import Main from "../ui/Main";
import Container from "../ui/Container";
import { Outlet } from "react-router-dom";

function AppLayout() {
  return (
    <div className="grid grid-cols-[18rem_1fr] grid-rows-[auto_1fr] h-screen text-gray-800">
      <SideBar />
      <Header />
      <Main>
        <Container>
          <Outlet />
        </Container>
      </Main>
    </div>
  );
}

export default AppLayout;
