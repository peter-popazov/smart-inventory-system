import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import PropTypes from "prop-types";
import SpinnerFS from "./SpinnerFS";

function ProtectedRoute({ children }) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [isUserLoaded, setIsUserLoaded] = useState(false);

  // queryClient.setQueryData({
  //   email: "peter@gmail.com",
  //   firstName: null,
  //   lastName: null,
  //   role: "ADMIN",
  //   jwt_token:
  //     "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InBldGVyQGdtYWlsLmNvbSIsInN1YiI6InBldGVyQGdtYWlsLmNvbSIsImlhdCI6MTczMTA4MDQxMCwiZXhwIjoxNzMxMTY2ODEwLCJhdXRob3JpdGllcyI6W119.VksTHHLSsQc_VWiTc8AEomawhkiGcI3FR0HGsjkZIAHINPRRiPyoX1MLbxGi9KrR",
  // });

  useEffect(() => {
    const user = queryClient.getQueryData("user");

    if (!user) {
      navigate("/auth/login");
    } else {
      setIsUserLoaded(true);
    }
  }, [navigate, queryClient]);

  if (!isUserLoaded) {
    return <SpinnerFS />;
  }

  return children;
}

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ProtectedRoute;
