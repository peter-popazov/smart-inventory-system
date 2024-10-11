import PropTypes from "prop-types";
import { createContext, useContext } from "react";

const TableContext = createContext();
const commonRow = `grid gap-x-4 items-center transition-none text-center`;

function Table({ cols, children }) {
  return (
    <TableContext.Provider value={{ cols }}>
      <table className="w-full border-collapse overflow-hidden rounded-b-xl bg-white">
        {children}
      </table>
    </TableContext.Provider>
  );
}

Table.propTypes = {
  cols: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};

function Header({ headers }) {
  const { cols } = useContext(TableContext);
  return (
    <thead>
      <tr
        className={`${commonRow} ${cols} text-md border-b border-b-gray-300 px-4 py-4 font-bold tracking-wide text-gray-800`}
      >
        {headers.map((header, index) => (
          <td key={index}>{header}</td>
        ))}
      </tr>
    </thead>
  );
}

Header.propTypes = {
  headers: PropTypes.arrayOf(PropTypes.string).isRequired,
};

function Row({ children }) {
  const { cols } = useContext(TableContext);
  return <tr className={`${commonRow} ${cols} h-16`}>{children}</tr>;
}

Row.propTypes = {
  children: PropTypes.node.isRequired,
};

function Body({ children }) {
  return <tbody className="rounded-b-xl text-gray-700">{children}</tbody>;
}

Body.propTypes = {
  children: PropTypes.node.isRequired,
};

function Footer({ children }) {
  return (
    <tfoot>
      <tr>
        <td colSpan="100%">
          <div
            className={`flex h-16 items-center justify-center rounded-b-xl border-t border-t-gray-300`}
          >
            {children}
          </div>
        </td>
      </tr>
    </tfoot>
  );
}

Footer.propTypes = {
  children: PropTypes.node.isRequired,
};

Table.Header = Header;
Table.Row = Row;
Table.Body = Body;
Table.Footer = Footer;

export default Table;
