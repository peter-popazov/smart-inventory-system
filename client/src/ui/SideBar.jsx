import { useIsOpen } from "@/context/SidebarContext.jsx";
import Logo from "./Logo";
import MainNav from "./MainNav";
import { ICONS_SIZE } from "@/constants/iconSize";
import { RxHamburgerMenu } from "react-icons/rx";
import { IoMdClose } from "react-icons/io";

function SideBar() {
  const { isSidebarOpen, toggleIsOpen } = useIsOpen();

  return (
    <aside
      className={`row-span-full flex flex-col gap-3 rounded-[35px] bg-gray-100 transition-transform ${
        isSidebarOpen ? "h-screen translate-x-0 p-6" : "-translate-x-full"
      }`}
    >
      <div className={`flex lg:justify-center justify-between`}>
        {isSidebarOpen && <Logo showLogo={isSidebarOpen} />}
        <button onClick={toggleIsOpen} className="block lg:hidden self-start">
          {!isSidebarOpen ? (
            <RxHamburgerMenu size={ICONS_SIZE} />
          ) : (
            <IoMdClose size={ICONS_SIZE} />
          )}
        </button>
      </div>

      <MainNav />
    </aside>
  );
}

export default SideBar;
