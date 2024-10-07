import { Link } from "react-router-dom";

function Logo() {
  return (
    <Link
      to="/"
      className="flex justify-center gap-6 mt-4 hover:-translate-y-1 transition-all duration-300 "
    >
      <div className="flex justify-center gap-6">
        <div className="w-16 h-16">
          <img src="logo.png" alt="Logo" />
        </div>
        <p className="flex items-center text-2xl font-bold uppercase">
          Easy Store
        </p>
      </div>
    </Link>
  );
}

export default Logo;
