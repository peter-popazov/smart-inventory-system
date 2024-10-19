import Logo from "./Logo";
import MainNav from "./MainNav";

function SideBar() {
  return (
    <aside className="row-span-full hidden flex-col gap-3 rounded-[35px] bg-gray-100 p-6 lg:flex">
      <Logo />
      <MainNav />
    </aside>
  );
}

export default SideBar;
