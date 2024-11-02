import { Loader2 } from "lucide-react";
import PropTypes from "prop-types";

function SpinnerFS({ size = 48, className = "" }) {
  return (
    <div className="bg-backdrop fixed inset-0 z-50 flex items-center justify-center backdrop-blur-sm">
      <Loader2
        className={`animate-spin text-gray-800 ${className}`}
        size={size}
      />
    </div>
  );
}

SpinnerFS.propTypes = {
  size: PropTypes.number,
  className: PropTypes.string,
};

export default SpinnerFS;
