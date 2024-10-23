import PropTypes from "prop-types";

function PageHeader({ icon, children }) {
  return (
    <header className="flex h-16 shrink-0 items-center border-b px-4 md:px-6">
      {icon && <span className="mr-4">{icon}</span>}
      <h1 className="text-lg font-semibold">{children}</h1>
    </header>
  );
}

PageHeader.propTypes = {
  icon: PropTypes.node,
  children: PropTypes.string.isRequired,
};

export default PageHeader;
