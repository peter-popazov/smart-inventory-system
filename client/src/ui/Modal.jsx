import PropTypes from "prop-types";
import { IoMdClose } from "react-icons/io";
import { createPortal } from "react-dom";
import { cloneElement, createContext, useContext, useState } from "react";
import { useEscapeKey } from "../hooks/useEscapeKey";
// import { useOutsideClick } from "../../hooks/useOutsideClick";

const ModalContext = createContext();

function Modal({ children }) {
  const [openName, setOpenName] = useState("");
  const close = () => setOpenName("");
  const open = setOpenName;
  return (
    <ModalContext.Provider value={{ openName, open, close }}>
      {children}
    </ModalContext.Provider>
  );
}

Modal.propTypes = {
  children: PropTypes.node,
  onClose: PropTypes.func,
};

function Open({ children, opens: opensWindowName }) {
  const { open } = useContext(ModalContext);

  return cloneElement(children, {
    onClick: (e) => {
      if (children.props.onClick) {
        children.props.onClick(e);
      }
      open(opensWindowName);
    },
  });
}

Open.propTypes = {
  children: PropTypes.node,
  opens: PropTypes.string.isRequired,
};

function Window({ children, name, width = "w-full" }) {
  const { openName, close } = useContext(ModalContext);

  useEscapeKey(close);

  if (name !== openName) return null;

  return createPortal(
    <div
      className={`bg-backdrop fixed left-0 top-0 h-screen w-full overflow-y-auto backdrop-blur-sm transition-all duration-500`}
    >
      <div
        className={`fixed left-1/2 top-1/2 ${width} max-w-full -translate-x-1/2 sm:-translate-y-1/2 -translate-y-[25%] transform rounded-lg bg-gray-50 p-6 shadow-lg md:p-8 lg:h-auto lg:w-1/2`}
      >
        <button
          className="absolute right-7 top-6 translate-x-3 rounded-sm border-none bg-none p-1 transition-all duration-200 hover:bg-gray-100 focus:ring-gray-700 focus:ring-offset-2 md:top-3"
          onClick={close}
        >
          <IoMdClose />
        </button>
        <div>{cloneElement(children, { onCloseModal: close })}</div>
      </div>
    </div>,
    document.body,
  );
}

Window.propTypes = {
  children: PropTypes.node,
  name: PropTypes.string.isRequired,
  onClose: PropTypes.func,
  width: PropTypes.string,
};

Modal.Open = Open;
Modal.Window = Window;

export default Modal;
