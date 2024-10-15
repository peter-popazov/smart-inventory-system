import PropTypes from "prop-types";

function Input({
  type = "text",
  placeholder = "",
  value,
  onChange,
  onBlur,
  onFocus,
  disabled = false,
  required = false,
  error,
  icon,
  height = "h-9",
  useFormHook = {},
  children,
  ...props
}) {
  return (
    <div
      className={`group ${height} flex items-center rounded-xl border border-gray-400 px-2 py-2 transition-all duration-300 focus-within:-translate-y-1 focus-within:border-gray-700`}
    >
      {icon && <span className="ml-1 mr-3 text-gray-500">{icon}</span>}
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        onBlur={onBlur}
        onFocus={onFocus}
        disabled={disabled}
        required={required}
        {...useFormHook}
        {...props}
        className={`flex-grow bg-transparent placeholder:text-[16px] placeholder:text-slate-400 focus:outline-none ${
          error ? "border-red-500" : "border-gray-300"
        } ${disabled ? "cursor-not-allowed" : ""}`}
      />
      {children && <span className="flex items-center mr-2">{children}</span>} 
      {error && <span className="text-sm text-red-500">{error}</span>}
    </div>
  );
}

Input.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  value: PropTypes.string,
  onChange: PropTypes.func,
  onBlur: PropTypes.func,
  onFocus: PropTypes.func,
  disabled: PropTypes.bool,
  required: PropTypes.bool,
  error: PropTypes.string,
  icon: PropTypes.node,
  height: PropTypes.string,
  useFormHook: PropTypes.object,
  children: PropTypes.node,
};

export default Input;
