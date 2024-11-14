import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import PropTypes from "prop-types";
import SpinnerFS from "./SpinnerFS";

function ProtectedRoute({ children }) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [isUserLoaded, setIsUserLoaded] = useState(false);

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
