import { Link } from "react-router-dom";
import PropTypes from 'prop-types';

const sizes = {
  sm: "h-8 w-8",
  md: "h-16 w-16",
  lg: "h-20 w-20",
};

function Logo({ size = "md" }) {
  return (
    <Link
      to="/"
      className="mt-4 flex justify-center gap-6 transition-all duration-300 hover:-translate-y-1"
    >
      <div className="flex flex-col items-center justify-center gap-4">
        <div className={`flex ${sizes[size]} items-center`}>
          <img src="/logo.png" alt="Logo" />
        </div>
        <p className="flex items-center text-xl font-light uppercase tracking-widest">
          Easy Store
        </p>
      </div>
    </Link>
  );
}
Logo.propTypes = {
  size: PropTypes.oneOf(['sm', 'md', 'lg']),
};

export default Logo;
