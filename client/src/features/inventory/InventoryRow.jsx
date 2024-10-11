import Table from "../../ui/Table";

const item = {
  sku: "P12345",
  productName: "Wireless Mouse",
  price: 25.99,
  quantity: 100,
  category: "Electronics",
  minStockLevel: 10,
  provider: "TechSupply Inc.",
  image: "https://via.placeholder.com/50",
};

function InventoryRow() {
  return (
    <Table.Row>
      <td>
        <span>{item.sku}</span>
      </td>
      <td className="flex items-center">
        <div className="mr-4 flex h-16 items-center">
          <img src={item.image} alt={item.productName} />
        </div>
        <span>{item.productName}</span>
      </td>
      <td>
        <span></span>${item.price}
      </td>
      <td>
        <span>{item.quantity}</span>
      </td>
      <td>
        <span>{item.category}</span>
      </td>
      <td>
        <span>{item.minStockLevel}</span>
      </td>
      <td>
        <span>{item.provider}</span>
      </td>
    </Table.Row>
  );
}

export default InventoryRow;
