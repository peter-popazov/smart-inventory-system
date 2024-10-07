import PropTypes from "prop-types";

function Container({ children }) {
  return (
    <div className="max-w-[120rem] mx-auto flex flex-col gap-2">{children}</div>
  );
}

Container.propTypes = {
  children: PropTypes.node,
};

export default Container;
