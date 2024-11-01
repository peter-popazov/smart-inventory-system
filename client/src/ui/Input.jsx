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
  useFormHook = {},
  children,
  rounded = "rounded-xl",
  className,
  ...props
}) {
  return (
    <div
      className={`group flex ${className} ${rounded} w-16 items-center border border-gray-400 px-2 py-2 transition-all duration-300 focus-within:-translate-y-1 focus-within:border-gray-700`}
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
        className={`flex-grow bg-transparent placeholder:text-[14px] placeholder:text-slate-400 focus:outline-none ${
          error ? "border-red-500" : "border-gray-300"
        } ${disabled ? "cursor-not-allowed" : ""}`}
      />
      {children && <span className="mr-2 flex items-center">{children}</span>}
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
  className: PropTypes.string,
  rounded: PropTypes.string,
};

export default Input;
