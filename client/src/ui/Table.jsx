import PropTypes from "prop-types";
import { createContext, useContext } from "react";

const TableContext = createContext();
const commonRow = `grid gap-x-4 items-center transition-none`;

function Table({ cols, children }) {
  return (
    <TableContext.Provider value={{ cols }}>
      <div className="overflow-x-auto">
        <table className="min-w-full border-collapse rounded-b-xl bg-white text-sm">
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
        className={`${commonRow} ${cols} text-md border-b border-b-gray-300 p-4 font-bold tracking-wide text-gray-600`}
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

function Row({ children, bgColor }) {
  const { cols } = useContext(TableContext);
  return (
    <tr
      className={`${commonRow} ${cols} ${bgColor} min-h-10 w-full border-b border-b-gray-200 px-4 hover:bg-gray-50`}
    >
      {children}
    </tr>
  );
}

Row.propTypes = {
  children: PropTypes.node.isRequired,
  bgColor: PropTypes.string,
};

function Body({ data, render, hilightRow }) {
  if (data.length === 0) {
    return (
      <tbody className="overflow-x-auto text-gray-700">
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

  return (
    <tbody className="text-gray-700">
      {data.map((item, index) => (
        <Row
          key={index}
          bgColor={hilightRow && hilightRow(item) ? "!bg-violet-100 rounded-lg my-1.5" : ""}
        >
          {render(item)}
        </Row>
      ))}
    </tbody>
  );
}

Body.propTypes = {
  data: PropTypes.array.isRequired,
  render: PropTypes.func.isRequired,
  hilightRow: PropTypes.func,
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
