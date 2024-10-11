import PropTypes from "prop-types";

function Main({ children }) {
  return (
    <main className="ml-3 rounded-3xl bg-[#E7DDFC] bg-opacity-30">
      {children}
    </main>
  );
}

Main.propTypes = {
  children: PropTypes.node,
};

export default Main;
