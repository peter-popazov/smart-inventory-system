import { saveAs } from "file-saver";

export function exportToJSON(data, filename = "data.json") {
  const jsonString = JSON.stringify(data, null, 2);

  const blob = new Blob([jsonString], { type: "application/json" });

  saveAs(blob, filename);
}
