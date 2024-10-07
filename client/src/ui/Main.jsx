import PropTypes from "prop-types";

function Main({ children }) {
  return <main className="bg-gray-50">{children}</main>;
}

Main.propTypes = {
  children: PropTypes.node,
};

export default Main;
