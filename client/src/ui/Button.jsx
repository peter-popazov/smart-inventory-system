import PropTypes from "prop-types";

const baseStyles = "font-semibold focus:outline-none transition";

const sizes = {
  iconOnly: "p-2",
  sm: "px-3 py-1 text-sm",
  md: "px-4 py-2 text-[14px]",
  lg: "px-6 py-3 text-lg",
};

function Button({
  type = "button",
  size = "md",
  bgColor = "",
  rounded = "",
  textColor = "text-gray-800",
  onClick,
  disabled = false,
  children,
  icon,
  className,
  hideText = true,
}) {
  return (
    <button
      type={type}
      className={`${baseStyles} ${bgColor} ${textColor} ${rounded} ${sizes[size]} transition-all duration-300 focus:ring-2 focus:ring-yellow-400 focus:ring-offset-2 ${className} ${
        disabled ? "cursor-not-allowed opacity-50" : ""
      } ${icon ? "flex items-center justify-center gap-2" : ""}`}
      onClick={onClick}
      disabled={disabled}
    >
      {icon && <span>{icon}</span>}
      {children && (
        <span className={`${icon && hideText && "hidden md:block"}`}>
          {children}
        </span>
      )}
    </button>
  );
}

Button.propTypes = {
  type: PropTypes.oneOf(["button", "submit", "reset"]),
  size: PropTypes.oneOf(["sm", "md", "lg"]),
  bgColor: PropTypes.string,
  textColor: PropTypes.string,
  rounded: PropTypes.string,
  className: PropTypes.string,
  onClick: PropTypes.func,
  disabled: PropTypes.bool,
  hideText: PropTypes.bool,
  children: PropTypes.node,
  icon: PropTypes.node,
};

export default Button;
