import PropTypes from "prop-types";

function Button({
  type = "button",
  size = "md",
  bgColor = "",
  rounded = "",
  textColor = "text-white",
  onClick,
  disabled = false,
  children,
  icon,
}) {
  const baseStyles = "font-semibold focus:outline-none transition";

  const sizes = {
    sm: "px-3 py-1 text-sm",
    md: "px-4 py-2 text-[14px]",
    lg: "px-6 py-3 text-lg",
  };

  return (
    <button
      type={type}
      className={`${baseStyles} ${bgColor} ${textColor} ${rounded} ${sizes[size]} focus:ring-2 focus:ring-yellow-400 focus:ring-offset-2 ${
        disabled ? "cursor-not-allowed opacity-50" : ""
      } ${icon ? "flex items-center justify-center gap-2" : ""}`}
      onClick={onClick}
      disabled={disabled}
    >
      {icon && <span>{icon}</span>}
      <span>{children}</span>
    </button>
  );
}

Button.propTypes = {
  type: PropTypes.oneOf(["button", "submit", "reset"]),
  size: PropTypes.oneOf(["sm", "md", "lg"]),
  bgColor: PropTypes.string,
  textColor: PropTypes.string,
  rounded: PropTypes.string,
  onClick: PropTypes.func,
  disabled: PropTypes.bool,
  children: PropTypes.node.isRequired,
  icon: PropTypes.node,
};

export default Button;
