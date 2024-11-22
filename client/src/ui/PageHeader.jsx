import { ICONS_SIZE } from "@/constants/iconSize";
import { useIsOpen } from "@/context/SidebarContext";
import PropTypes from "prop-types";
import { RxHamburgerMenu } from "react-icons/rx";

function PageHeader({ icon, children }) {
  const { toggleIsOpen, isSidebarOpen } = useIsOpen();
  return (
    <header className="flex h-16 shrink-0 items-center justify-between border-b px-4 md:px-6">
      <div className="flex">
        {icon && <span className="mr-4">{icon}</span>}
        <h1 className="text-lg font-semibold">{children}</h1>
      </div>
      <div className={`${isSidebarOpen && "hidden"} lg:hidden block`}>
        <button onClick={toggleIsOpen}>
          <RxHamburgerMenu size={ICONS_SIZE} />
        </button>
      </div>
    </header>
  );
}

PageHeader.propTypes = {
  icon: PropTypes.node,
  children: PropTypes.string.isRequired,
};

export default PageHeader;
