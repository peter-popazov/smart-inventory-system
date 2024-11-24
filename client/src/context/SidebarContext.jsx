import PropTypes from "prop-types";
import { createContext, useState, useContext, useEffect } from "react";

const SidebarContext = createContext();

export const useIsOpen = () => useContext(SidebarContext);

export const SidebarProvider = ({ children }) => {
  const [isSidebarOpen, setIsOpen] = useState(true);
  const [isSmallWindow, setIsSmallWindow] = useState(true);
  
  const toggleIsOpen = () => {
    setIsOpen((prevIsOpen) => !prevIsOpen);
  };

  function checkWindowWidth() {
    const windowWidth = window.innerWidth;

    if (windowWidth < 1024) {
      setIsOpen(false);
      setIsSmallWindow(true);
    } else {
      setIsOpen(true);
      setIsSmallWindow(false);
    }
  }

  useEffect(() => {
    checkWindowWidth();

    const handleResize = () => {
      checkWindowWidth();
    };

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <SidebarContext.Provider value={{ isSidebarOpen, toggleIsOpen, isSmallWindow }}>
      {children}
    </SidebarContext.Provider>
  );
};

SidebarProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
