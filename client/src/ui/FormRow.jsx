import PropTypes from "prop-types";

function FormRow({ label, error, children }) {
  return (
    <div>
      {label && <label className="mb-2 block font-medium" htmlFor={children.props.id}>{label}</label>}
      {children}
      {error && <p className="text-red-600 text-sm mt-1">{error}</p>}
    </div>
  );
}

FormRow.propTypes = {
  children: PropTypes.node.isRequired,
  label: PropTypes.string.isRequired,
  error: PropTypes.string,
};

export default FormRow;
