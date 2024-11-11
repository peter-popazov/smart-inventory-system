import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import PropTypes from "prop-types";
import SpinnerFS from "./SpinnerFS";

function ProtectedRoute({ children }) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [isUserLoaded, setIsUserLoaded] = useState(false);

  queryClient.setQueryData({
    email: "peter@gmail.com",
    firstName: "Peter",
    lastName: "Popazov",
    role: "ADMIN",
    jwt_token:
      "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InBldGVyQGdtYWlsLmNvbSIsInN1YiI6InBldGVyQGdtYWlsLmNvbSIsImlhdCI6MTczMTI0ODY5NywiZXhwIjoxNzMxMzM1MDk3LCJhdXRob3JpdGllcyI6W119.M8RgoN3FqGe8B91pKC_-4Umje2R8g9JXwKSa2VIsvb58gGJWOHlzAT5t2KNM0oco",
  });

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
