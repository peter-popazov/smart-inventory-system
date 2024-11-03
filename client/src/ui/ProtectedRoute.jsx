import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import PropTypes from "prop-types";
import SpinnerFS from "./SpinnerFS";

function ProtectedRoute({ children }) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [isUserLoaded, setIsUserLoaded] = useState(false);

  queryClient.setQueryData("user", {
    email: "peter@gmail.com",
    firstName: "Peter",
    lastName: "Popazov",
    role: null,
    jwt_token:
      "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InBldGVyQGdtYWlsLmNvbSIsInN1YiI6InBldGVyQGdtYWlsLmNvbSIsImlhdCI6MTczMDY1MjIwMSwiZXhwIjoxNzMwNzM4NjAxLCJhdXRob3JpdGllcyI6W119.ttUG5DCEN8rZKj9Elh8dHcXVvAr9nWRIqgHpJciZ43Cma-YwrhI6sITR5phQ3jJc",
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
