import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import PropTypes from "prop-types";

function ProtectedRoute({ children }) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [isUserLoaded, setIsUserLoaded] = useState(false);

  useEffect(() => {
    const user = queryClient.getQueryData("user");
    console.log("user", user);

    if (!user) {
      navigate("/auth/login");
      console.log("NOT LOGGED IN", user);
    } else {
      setIsUserLoaded(true);
    }
  }, [navigate, queryClient]);

  if (!isUserLoaded) {
    return <div>Loading...</div>;
  }

  return children;
}

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ProtectedRoute;
