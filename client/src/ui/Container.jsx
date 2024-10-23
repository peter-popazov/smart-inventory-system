import PropTypes from "prop-types";

function Container({ children }) {
  return (
    <div className="mx-auto flex h-screen max-w-[120rem] flex-col overflow-y-scroll">
      {children}
    </div>
  );
}

Container.propTypes = {
  children: PropTypes.node,
};

export default Container;
