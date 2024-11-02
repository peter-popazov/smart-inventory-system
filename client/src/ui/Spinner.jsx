import { Loader2 } from "lucide-react";
import PropTypes from "prop-types";

function Spinner({ size = 24, className = "" }) {
  return (
    <Loader2
      className={`animate-spin text-gray-800 ${className}`}
      size={size}
    />
  );
}

Spinner.propTypes = {
  size: PropTypes.number,
  className: PropTypes.string,
};

export default Spinner;
