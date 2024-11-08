import { ICONS_SIZE } from "@/constants/iconSize";
import { FaRegUser } from "react-icons/fa";
import { IoLogInOutline } from "react-icons/io5";
import { useQueryClient } from "react-query";
import { useNavigate } from "react-router-dom";
import AddUserInfo from "./AddUserInfo";
import { capitalize } from "@/utils/utils";

function LoggedUser() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  function handleLogout() {
    queryClient.setQueryData("user", null);
    navigate("/auth/login");
  }

  const loggedInUser = queryClient.getQueryData("user");
  let isLoggedIn = false;

  if (loggedInUser?.firstName && loggedInUser?.lastName) {
    isLoggedIn = true;
  }

  return (
    <div className="-m-2 rounded-2xl bg-white p-3">
      {isLoggedIn ? (
        <div className="flex flex-row items-center gap-4 transition-all duration-300 hover:text-gray-800">
          <div>
            <FaRegUser size={ICONS_SIZE - 4} />
          </div>
          <div className="flex w-full flex-col gap-1">
            <div className="flex flex-row items-center justify-between">
              <p className="text-md font-bold">
                <span>{loggedInUser.firstName} </span>
                <span>{loggedInUser.lastName}</span>
              </p>
              <div className="mr-1 rounded-xl bg-blue-500 p-1 px-2 opacity-80">
                <p className="text text-[10px] text-gray-50">
                  {capitalize(loggedInUser.role)}
                </p>
              </div>
            </div>
            <div className="flex items-center justify-between">
              <p className="text-sm text-gray-500">{loggedInUser.email}</p>
              <div className="flex w-full justify-center">
                <button onClick={handleLogout}>
                  <IoLogInOutline size={ICONS_SIZE - 4} />
                </button>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <AddUserInfo />
      )}
    </div>
  );
}

export default LoggedUser;
