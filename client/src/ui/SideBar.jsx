import Logo from "./Logo";
import MainNav from "./MainNav";

function SideBar() {
  return (
    <aside className="p-6 row-span-full flex flex-col gap-3 bg-gray-100 rounded-[35px]">
      <Logo />
      <MainNav />
    </aside>
  );
}

export default SideBar;
