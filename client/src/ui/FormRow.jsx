import PropTypes from "prop-types";

function FormRow({ label, error, children, mb = "mb-2", ...props }) {
  return (
    <div {...props}>
      {label && (
        <label
          className={`${mb} block font-medium`}
          htmlFor={children.props.id}
        >
          {label}
        </label>
      )}
      {children}
      {error && <p className="mt-1 text-sm text-red-600">{error}</p>}
    </div>
  );
}

FormRow.propTypes = {
  children: PropTypes.node.isRequired,
  label: PropTypes.string.isRequired,
  mb: PropTypes.string,
  error: PropTypes.string,
};

export default FormRow;
