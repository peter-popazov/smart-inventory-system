import { HiOutlineUserGroup, HiOutlineHome } from "react-icons/hi";
import SideBarNavLink from "./SideBarNavLink";
import { MdOutlineInventory2 } from "react-icons/md";
import { IoAnalyticsOutline } from "react-icons/io5";
import { BiMessageDetail } from "react-icons/bi";
import { ICONS_SIZE } from "@/constants/iconSize";
import LoggedUser from "@/features/user/LoggedUser";

const navItems = [
  {
    to: "/dashboard",
    icon: <HiOutlineHome size={ICONS_SIZE} />,
    label: "Dashboard",
  },
  {
    to: "/team",
    icon: <HiOutlineUserGroup size={ICONS_SIZE} />,
    label: "Team",
  },
  {
    to: "/inventory",
    icon: <MdOutlineInventory2 size={ICONS_SIZE} />,
    label: "Inventory",
  },
  {
    to: "/analytics",
    icon: <IoAnalyticsOutline size={ICONS_SIZE} />,
    label: "Analytics",
  },
  {
    to: "/alerts",
    icon: <BiMessageDetail size={ICONS_SIZE} />,
    label: "Stock alerts",
  },
];

function MainNav() {
  return (
    <nav className="mt-4 flex h-full flex-col p-4">
      <ul className="flex flex-grow flex-col gap-6">
        {navItems.map((item, index) => (
          <SideBarNavLink item={item} key={index} />
        ))}
      </ul>

      <LoggedUser />
    </nav>
  );
}

export default MainNav;
