import { HiOutlineUserGroup, HiOutlineHome } from "react-icons/hi";
import { NavLink } from "react-router-dom";
import { MdOutlineInventory2 } from "react-icons/md";
import { IoAnalyticsOutline } from "react-icons/io5";
import { BiMessageDetail } from "react-icons/bi";
import { FaRegUser } from "react-icons/fa";

const ICONS_SIZE = 32;
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
    <nav className="mt-6 p-4 h-full flex flex-col">
      <ul className="flex flex-col gap-6 flex-grow">
        {navItems.map((item, index) => (
          <SideBarNavLink item={item} key={index} />
        ))}
      </ul>

      <div className="bg-white p-3 rounded-2xl -m-2">
        <div className="flex flex-row gap-7 items-center hover:text-gray-950 transition-all duration-300 ">
          <div>
            <FaRegUser size={ICONS_SIZE - 4} />
          </div>
          <div className="flex flex-col w-2/3 gap-1">
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
        <div className="flex flex-row gap-6 items-center hover:text-gray-950 transition-all duration-300">
          <div>{item.icon}</div>
          <span className="md:text-xl text-lg">{item.label}</span>
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
