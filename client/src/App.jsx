import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
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
import AuthLoyout from "./features/auth/AuthLoyout";
import LoginForm from "./features/auth/LoginForm";
import Messages from "@/pages/Messages";

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
        index: true,
        path: "/dashboard",
        element: <Dashboard />,
      },
      {
        index: true,
        path: "/team",
        element: <div>Team</div>,
      },
      {
        index: true,
        path: "/inventory",
        element: <Inventory />,
      },
      {
        index: true,
        path: "/analytics",
        element: <div>Analytics</div>,
      },
      {
        index: true,
        path: "/messages",
        element: <Messages />,
      },
    ],
  },
  {
    path: "/auth",
    element: <AuthLoyout />,
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

const queryClinet = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 5,
      refetchOnWindowFocus: false,
    },
  },
});

function App() {
  return (
    <QueryClientProvider client={queryClinet}>
      <RouterProvider router={router}></RouterProvider>
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
            maxWidth: "500pxm",
            padding: "16px 24px",
            backgroundColor: "var(--color-grey-0)",
            color: "var(--color-grey-700)",
          },
        }}
      />
    </QueryClientProvider>
  );
}

export default App;
