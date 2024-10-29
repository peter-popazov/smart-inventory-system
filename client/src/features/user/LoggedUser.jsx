import { ICONS_SIZE } from "@/constants/iconSize";
import Button from "@/ui/Button";
import { FaRegUser } from "react-icons/fa";
import { IoLogInOutline } from "react-icons/io5";
import { useQueryClient } from "react-query";
import { useNavigate } from "react-router-dom";

function LoggedUser() {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  function handleLogout() {
    queryClient.setQueryData("user", null);
    navigate("/auth/login");
  }
  return (
    <div className="-m-2 rounded-2xl bg-white p-3">
      <div className="flex flex-row items-center gap-7 transition-all duration-300 hover:text-gray-950">
        <div>
          <FaRegUser size={ICONS_SIZE - 4} />
        </div>
        <div className="flex w-2/3 flex-col gap-1">
          <div className="flex flex-row items-center justify-between">
            <span className="text-lg font-bold">John</span>
            <span className="text-sm font-thin">Admin</span>
            <Button
              onClick={handleLogout}
              type="iconOnly"
              icon={<IoLogInOutline size={ICONS_SIZE - 4} />}
            />
          </div>
          <span className="text-sm text-gray-500">user@gmail.com</span>
        </div>
      </div>
    </div>
  );
}

export default LoggedUser;
