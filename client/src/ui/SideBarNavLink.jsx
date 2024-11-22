import PropTypes from "prop-types";
import { NavLink } from "react-router-dom";
import NumberAlert from "./NumberAlert";
import { useAlerts } from "@/features/alerts/useAlerts";
import { useIsOpen } from "@/context/SidebarContext";

function SideBarNavLink({ item }) {
  const { data: stockAlerts = [] } = useAlerts();
  const alertsNumber = stockAlerts.length;

  const { toggleIsOpen, isSmallWindow } = useIsOpen();

  return (
    <li>
      <NavLink to={item.to} onClick={isSmallWindow ? toggleIsOpen : undefined}>
        <div className="hover:slate-gray-950 flex flex-row items-center gap-4 text-slate-700 transition-all duration-300">
          <div>{item.icon}</div>
          <div className="flex items-center gap-3">
            <span className="text-lg">{item.label}</span>
            {alertsNumber && item.to.includes("alerts") > 0 && (
              <NumberAlert>{alertsNumber}</NumberAlert>
            )}
          </div>
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
