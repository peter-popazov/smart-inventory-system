export function transformData(apiData) {
  return apiData.map((item) => {
    const warehouseNames = (item.inventories || [])
      .map((inv) => inv.warehouse?.name)
      .filter((name) => name) // This removes undefined or null names
      .join(", ");
    return {
      productId: item.productId,
      SKU: item.productCode,
      barCode: item.barCode,
      product: item.name,
      description: item.description,
      photoUrl: item.photoUrl,
      price: item.price,
      category: item.categoryName,
      reorderLevel: item.minStockLevel,
      maxStockLevel: item.maxStockLevel,
      quantity: item.inventories.reduce(
        (acc, cur) => acc + cur.stockAvailable,
        0,
      ),
      inventories: item.inventories,
      warehouseNames: warehouseNames,
    };
  });
}

export function capitalize(str) {
  if (!str) return "";
  return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

export function filterMembers(teamMembers, searchTerm) {
  return teamMembers.filter(
    (member) =>
      (member.firstName?.toLowerCase() || "").includes(
        searchTerm.toLowerCase(),
      ) ||
      (member.lastName?.toLowerCase() || "").includes(
        searchTerm.toLowerCase(),
      ) ||
      (member.email?.toLowerCase() || "").includes(searchTerm.toLowerCase()),
  );
}

export function getFullName(member) {
  return `${member.firstName || ""} ${member.lastName || ""}`;
}

export function formatCurrency(amount, currency = "USD", locale = "en-US") {
  const formatter = new Intl.NumberFormat(locale, {
    style: "currency",
    currency: currency,
  });

  return formatter.format(amount);
}
