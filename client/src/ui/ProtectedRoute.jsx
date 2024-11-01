import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import PropTypes from "prop-types";

function ProtectedRoute({ children }) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [isUserLoaded, setIsUserLoaded] = useState(false);

  queryClient.setQueryData(["user"], {
    jwt_token:
      "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InBldGVyQGdtYWlsLmNvbSIsInN1YiI6InBldGVyQGdtYWlsLmNvbSIsImlhdCI6MTczMDQ5MDUxMCwiZXhwIjoxNzMwNTc2OTEwLCJhdXRob3JpdGllcyI6W119.Gace1NmW1oVnTqqpT4FgWaD6_tZPg6mblsDjE7kSAPslDlYQKHiKliRPJTny1EWT",
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
    return <div>Loading...</div>;
  }

  return children;
}

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ProtectedRoute;
