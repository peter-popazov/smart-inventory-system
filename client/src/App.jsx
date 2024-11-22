import { QueryClient, QueryClientProvider } from "react-query";
import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import AppLayout from "./pages/AppLayout";
import { Toaster } from "react-hot-toast";
import Inventory from "./pages/Inventory";
import Dashboard from "./pages/Dashboard";
import RegisterForm from "./features/auth/RegisterForm";
import AuthLayout from "./features/auth/AuthLayout";
import LoginForm from "./features/auth/LoginForm";
import Messages from "@/pages/Alerts";
import Team from "./pages/Team";
import ProtectedRoute from "./ui/ProtectedRoute";
import AllMovements from "./pages/AllMovements";
import { SidebarProvider } from "./context/SidebarContext";

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      {
        index: true,
        element: <Navigate replace to="dashboard" />,
      },
      {
        path: "/dashboard",
        element: (
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        ),
      },
      {
        path: "/team",
        element: (
          <ProtectedRoute>
            <Team />
          </ProtectedRoute>
        ),
      },
      {
        path: "/inventory",
        element: (
          <ProtectedRoute>
            <Inventory />
          </ProtectedRoute>
        ),
      },
      {
        path: "/alerts",
        element: (
          <ProtectedRoute>
            <Messages />
          </ProtectedRoute>
        ),
      },
      {
        path: "/movements",
        element: (
          <ProtectedRoute>
            <AllMovements />
          </ProtectedRoute>
        ),
      },
    ],
  },
  {
    path: "/auth",
    element: <AuthLayout />,
    children: [
      {
        index: true,
        element: <Navigate replace to="login" />,
      },
      {
        path: "/auth/login",
        element: <LoginForm />,
      },
      {
        path: "/auth/register",
        element: <RegisterForm />,
      },
    ],
  },
  {
    path: "*",
    element: <div>Not Found</div>,
  },
]);

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 5,
      refetchOnWindowFocus: true,
    },
  },
});

function App() {
  return (
    <SidebarProvider>
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
        <Toaster
          position="top-center"
          gutter={12}
          containerStyle={{ margin: "8px" }}
          toastOptions={{
            success: {
              duration: 3000,
            },
            error: {
              duration: 5000,
            },
            style: {
              fontSize: "16px",
              maxWidth: "500px",
              padding: "16px 24px",
              backgroundColor: "#fff",
              color: "#000",
            },
          }}
        />
      </QueryClientProvider>
    </SidebarProvider>
  );
}

export default App;
