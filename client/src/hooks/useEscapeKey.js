import { useEffect } from "react";

export function useEscapeKey(handler) {
  useEffect(() => {
    function handleKeyDown(e) {
      if (e.key === "Escape") {
        handler();
      }
    }

    document.addEventListener("keydown", handleKeyDown);
    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, [handler]);
}
