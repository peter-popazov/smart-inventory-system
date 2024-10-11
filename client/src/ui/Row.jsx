import PropTypes from "prop-types";

const rowType = {
  horizontal: "justify-between items-center",
  vertical: "flex-col",
};

function Row({ type = "vertical", gap = "gap-4", children }) {
  return <div className={`flex ${rowType[type]} ${gap}`}>{children}</div>;
}

Row.propTypes = {
  type: PropTypes.string,
  gap: PropTypes.string,
  children: PropTypes.node.isRequired,
};

export default Row;
