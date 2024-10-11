import PropTypes from "prop-types";

function Container({ children }) {
  return <div className="flex flex-col mx-auto h-screen max-w-[120rem]">{children}</div>;
}

Container.propTypes = {
  children: PropTypes.node,
};

export default Container;
