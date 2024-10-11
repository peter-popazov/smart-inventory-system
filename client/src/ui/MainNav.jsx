import { HiOutlineUserGroup, HiOutlineHome } from "react-icons/hi";
import { NavLink } from "react-router-dom";
import { MdOutlineInventory2 } from "react-icons/md";
import { IoAnalyticsOutline } from "react-icons/io5";
import { BiMessageDetail } from "react-icons/bi";
import { FaRegUser } from "react-icons/fa";

const ICONS_SIZE = 24;
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
    to: "/messages",
    icon: <BiMessageDetail size={ICONS_SIZE} />,
    label: "Messages",
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

      <div className="-m-2 rounded-2xl bg-white p-3">
        <div className="flex flex-row items-center gap-7 transition-all duration-300 hover:text-gray-950">
          <div>
            <FaRegUser size={ICONS_SIZE - 4} />
          </div>
          <div className="flex w-2/3 flex-col gap-1">
            <div className="flex flex-row items-center justify-between">
              <span className="text-lg font-bold">John</span>
              <span className="text-sm font-thin">Admin</span>
            </div>
            <span className="text-sm text-gray-500">user@gmail.com</span>
          </div>
        </div>
      </div>
    </nav>
  );
}

import PropTypes from "prop-types";

function SideBarNavLink({ item }) {
  return (
    <li>
      <NavLink to={item.to}>
        <div className="hover:slate-gray-950 flex flex-row items-center gap-4 text-slate-700 transition-all duration-300">
          <div>{item.icon}</div>
          <span className="text-lg">{item.label}</span>
        </div>
      </NavLink>
    </li>
  );
}
SideBarNavLink.propTypes = {
  item: PropTypes.shape({
    to: PropTypes.string.isRequired,
    icon: PropTypes.element.isRequired,
    label: PropTypes.string.isRequired,
  }).isRequired,
};

export default MainNav;
