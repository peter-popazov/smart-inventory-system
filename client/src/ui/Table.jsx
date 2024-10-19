import PropTypes from "prop-types";
import { createContext, useContext } from "react";

const TableContext = createContext();
const commonRow = `grid gap-x-4 items-center transition-none`;

function Table({ cols, children }) {
  return (
    <TableContext.Provider value={{ cols }}>
      <div className="overflow-x-auto">
        <table className="min-w-full border-collapse rounded-b-xl bg-white">
          {children}
        </table>
      </div>
    </TableContext.Provider>
  );
}

Table.propTypes = {
  cols: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};

function Header({ data, render }) {
  const { cols } = useContext(TableContext);
  return (
    <thead>
      <tr
        className={`${commonRow} ${cols} text-md border-b border-b-gray-300 p-4 font-bold tracking-wide text-gray-800`}
      >
        {data.map(render)}
      </tr>
    </thead>
  );
}

Header.propTypes = {
  data: PropTypes.arrayOf(PropTypes.string).isRequired,
  render: PropTypes.func.isRequired,
};

function Row({ children }) {
  const { cols } = useContext(TableContext);
  return (
    <tr
      className={`${commonRow} ${cols} h-16 w-full border-b border-b-gray-200 px-4`}
    >
      {children}
    </tr>
  );
}

Row.propTypes = {
  children: PropTypes.node.isRequired,
};

function Body({ data, render }) {
  if (data.length === 0) {
    return (
      <tbody className="text-gray-700">
        <tr>
          <td colSpan="100%">
            <div className="flex h-16 items-center justify-center rounded-b-xl">
              No data available
            </div>
          </td>
        </tr>
      </tbody>
    );
  }
  return <tbody className="text-gray-700">{data.map(render)}</tbody>;
}

Body.propTypes = {
  data: PropTypes.array.isRequired,
  render: PropTypes.func.isRequired,
};

function Footer({ children }) {
  return (
    <tfoot>
      <tr>
        <td colSpan="100%">
          <div className={`flex h-16 items-center justify-center rounded-b-xl`}>
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
