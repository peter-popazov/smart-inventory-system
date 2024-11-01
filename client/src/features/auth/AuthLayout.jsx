import { Link, Outlet, useLocation } from "react-router-dom";
import Logo from "../../ui/Logo";

function AuthLayout() {
  const location = useLocation();
  const isRegister = location.pathname.includes("register");
  return (
    <main className="min-h-screen w-full bg-transparent text-gray-800 md:overflow-y-hidden md:bg-gradient-to-br md:from-[#ffff] md:from-20% md:via-[#EBD0FC] md:via-100%">
      <div className="mx-auto max-w-[100rem]">
        <div className="grid h-screen grid-cols-1 md:grid-cols-[36rem_1fr]">
          <aside className="mx-auto mt-10 w-[90%] md:w-[26rem]">
            <Logo size="lg" />
            <Outlet />
            <div className="my-4 flex items-center justify-center">
              <hr className="w-full border-t border-gray-300" />
              <span className="px-4 text-gray-500">OR</span>
              <hr className="w-full border-t border-gray-300" />
            </div>

            <div className="flex justify-center">
              <span>
                {isRegister ? "Already a user?" : "You are new here?"}
              </span>
              <span>&nbsp;</span>
              <span className="text-blue-700 underline hover:text-blue-900">
                <Link to={`/auth/${isRegister ? "login" : "register"}`}>
                  {isRegister ? "Login" : "Register"}
                </Link>
              </span>
            </div>
          </aside>

          <div className="mx-auto mt-8 hidden max-w-[52rem] items-center px-8 md:mt-16 md:flex md:flex-col">
            <div>
              <h1 className="text-center text-2xl font-bold text-gray-800 lg:text-4xl">
                Effortlessly organize, manage, and access your storage with our
                web application{" "}
                <span className="text-violet-800">all in one place!</span>
              </h1>
            </div>
            <figure className="flex flex-grow items-center justify-center">
              <img
                src="/hero-storage-van.png"
                alt="Storage van"
                className="h-auto max-h-[60vh] w-full object-contain"
              />
            </figure>
          </div>
        </div>
      </div>
    </main>
  );
}

export default AuthLayout;
