import PropTypes from "prop-types";
import { NavLink } from "react-router-dom";

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

export default SideBarNavLink;
