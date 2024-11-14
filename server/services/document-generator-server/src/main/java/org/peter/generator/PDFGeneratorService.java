package org.peter.generator;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.peter.generator.dto.LowStockProduct;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PDFGeneratorService {

    public byte[] generateOrderProductPdf(LowStockProduct lowStockProduct) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Product Order Form")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(24)
                    .setBold()
                    .setUnderline());

            Table customerSupplierInfo = new Table(new float[]{250f, 250f});
            customerSupplierInfo.setWidth(UnitValue.createPercentValue(100));
            customerSupplierInfo.setMarginTop(20);

            customerSupplierInfo.addCell(createStyledCell("Customer Information", 12, true));
            customerSupplierInfo.addCell(createStyledCell("Supplier Information", 12, true));

            // Add details fields
            customerSupplierInfo.addCell(createStyledCell("Name: " + lowStockProduct.username()));
            customerSupplierInfo.addCell(createStyledCell("Name: _____________________________"));

            customerSupplierInfo.addCell(createStyledCell("Email: " + lowStockProduct.userEmail()));
            customerSupplierInfo.addCell(createStyledCell("Email: _____________________________"));

            customerSupplierInfo.addCell(createStyledCell("Address: _____________________________"));
            customerSupplierInfo.addCell(createStyledCell("Address: _____________________________"));

            document.add(customerSupplierInfo);

            document.add(new Paragraph("\nOrder Details:").setFontSize(16).setBold().setMarginTop(20));

            float[] pointColumnWidths = {200f, 100f, 100f, 100f};
            Table table = new Table(pointColumnWidths);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginTop(10);

            table.addCell(createStyledCell("Product Name", 12, true));
            table.addCell(createStyledCell("Quantity", 12, true));
            table.addCell(createStyledCell("Unit Price", 12, true));
            table.addCell(createStyledCell("Subtotal Price", 12, true));

            int reorderQuantity = lowStockProduct.maxStock() - lowStockProduct.currentStock();
            BigDecimal price = lowStockProduct.price();
            table.addCell(createStyledCell(lowStockProduct.productName()));
            table.addCell(createStyledCell(Integer.toString(reorderQuantity)));
            table.addCell(createStyledCell(price.toString()));
            table.addCell(createStyledCell(price.multiply(BigDecimal.valueOf(reorderQuantity)).toString()));

            document.add(table);

            document.add(new Paragraph("\nDiscount: ____________")
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(14)
                    .setMarginTop(20));

            document.add(new Paragraph("\nTotal Price: ____________")
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(14)
                    .setMarginTop(20));

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            document.add(new Paragraph("\nDate: " + now.format(timeFormatter)));
            document.add(new Paragraph("\nSignature: ____________"));

            document.close();
            return byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createTextField(PdfAcroForm form, PdfDocument pdfDoc, String fieldName, float x, float y, float width, float height) {
        PdfFormField textField = PdfTextFormField.createText(pdfDoc, new Rectangle(x, y, width, height), fieldName, "");
        form.addField(textField);
    }

    private Cell createStyledCell(String content) {
        return createStyledCell(content, 10, false);
    }

    private Cell createStyledCell(String content, int fontSize, boolean isHeader) {
        Cell cell = new Cell().add(new Paragraph(content).setFontSize(fontSize));
        cell.setPadding(8);
        if (isHeader) {
            cell.setBold();
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
        }
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
}
