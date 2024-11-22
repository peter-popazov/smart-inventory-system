import PropTypes from "prop-types";

function NumberAlert({ children }) {
  return (
    <span className="bg-rose-100 flex h-6 w-6 items-center justify-center rounded-full text-sm text-red-500">
      {children}
    </span>
  );
}

NumberAlert.propTypes = {
  children: PropTypes.node.isRequired,
};

export default NumberAlert;
