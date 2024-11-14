package org.peter.generator;

import lombok.RequiredArgsConstructor;
import org.peter.generator.dto.LowStockProduct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pdfs")
@RequiredArgsConstructor
public class PDFGeneratorController {

    private final PDFGeneratorService pdfGeneratorService;

    @PostMapping("/order")
    public ResponseEntity<byte[]> generateOrderProductPdf(@RequestBody LowStockProduct lowStockProduct) {
        byte[] pdfBytes = pdfGeneratorService.generateOrderProductPdf(lowStockProduct);

        if (pdfBytes == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
