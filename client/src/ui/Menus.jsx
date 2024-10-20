import PropTypes from "prop-types";
import { createContext, useContext, useState } from "react";
import { HiDotsVertical } from "react-icons/hi";
import { createPortal } from "react-dom";
import { useOutsideClick } from "../../hooks/useOutsideClick";

const MenusContext = createContext();

function Menus({ children }) {
  const [openId, setOpenId] = useState();
  const [pos, setPos] = useState(null);
  const close = () => setOpenId("");
  const open = setOpenId;
  return (
    <MenusContext.Provider value={{ openId, close, open, pos, setPos }}>
      {children}
    </MenusContext.Provider>
  );
}

Menus.propTypes = {
  children: PropTypes.node.isRequired,
};

function Menu({ children }) {
  return <div className="flex items-center justify-end">{children}</div>;
}

Menu.propTypes = {
  children: PropTypes.node.isRequired,
};

function Toggle({ id }) {
  const { openId, open, close, setPos } = useContext(MenusContext);

  function handleClick(e) {
    openId === "" || openId !== id ? open(id) : close();
    const pos = e.target.closest("button").getBoundingClientRect();
    console.log(pos);
    setPos({
      x: window.innerWidth - pos.width - pos.x,
      y: pos.y + pos.height + 8,
    });
  }

  return (
    <button
      id={id}
      onClick={handleClick}
      className="translate-x-[0.8rem] rounded-sm border-none bg-none p-2 transition-all duration-200 hover:bg-gray-100"
    >
      <HiDotsVertical />
    </button>
  );
}

Toggle.propTypes = {
  id: PropTypes.string.isRequired,
};

function List({ id, children }) {
  const { openId, pos, close } = useContext(MenusContext);
  const { ref } = useOutsideClick(close);
  if (openId !== id) return null;
  return createPortal(
    <ul
      className="fixed rounded-md bg-white shadow-md"
      style={{ right: `${pos.x}px`, top: `${pos.y}px` }}
      ref={ref}
    >
      {children}
    </ul>,
    document.body,
  );
}

List.propTypes = {
  id: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};

function Button({ children, icon, onClick }) {
  const { close } = useContext(MenusContext);
  function handleClick() {
    onClick?.();
    close();
  }
  return (
    <li>
      <button
        className="flex w-full items-center gap-4 border-none bg-none p-3 text-left text-sm text-gray-700 transition-all hover:bg-gray-50 hover:text-gray-900 md:px-4 md:py-2.5"
        onClick={handleClick}
      >
        <span>{icon}</span> {children}
      </button>
    </li>
  );
}

Button.propTypes = {
  children: PropTypes.node.isRequired,
  icon: PropTypes.node,
  onClick: PropTypes.func,
};

Menus.Menu = Menu;
Menus.Toggle = Toggle;
Menus.List = List;
Menus.Button = Button;

export default Menus;
