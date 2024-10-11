import { Link } from "react-router-dom";

function Logo() {
  return (
    <Link
      to="/"
      className="mt-4 flex justify-center gap-6 transition-all duration-300 hover:-translate-y-1"
    >
      <div className="flex flex-col items-center justify-center gap-4">
        <div className="flex h-16 w-16 items-center">
          <img src="logo.png" alt="Logo" />
        </div>
        <p className="flex items-center text-xl font-light uppercase tracking-widest">
          Easy Store
        </p>
      </div>
    </Link>
  );
}

export default Logo;
