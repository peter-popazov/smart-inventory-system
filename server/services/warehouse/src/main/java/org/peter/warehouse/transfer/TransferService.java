package org.peter.warehouse.transfer;

import lombok.RequiredArgsConstructor;
import org.peter.warehouse.dto.TransferRequest;
import org.peter.warehouse.exception.TransferNotFoundException;
import org.peter.warehouse.helpers.WarehousesMapper;
import org.peter.warehouse.warehouse.Warehouse;
import org.peter.warehouse.warehouse.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final WarehouseRepository warehouseRepository;
    private final TransferRepository transferRepository;


    public void transferProduct(TransferRequest request) {

        Warehouse warehouseTo = warehouseRepository.findById(request.warehouseIdTo()).
                orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Warehouse warehouseFrom = warehouseRepository.findById(request.warehouseIdFrom())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        if (warehouseTo.isRefrigerated() == warehouseFrom.isRefrigerated()) {
            throw new RuntimeException("Destination warehouse not refrigerated");
        }

        TransferStatus status;
        try {
            status = TransferStatus.valueOf(request.status());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transfer status: " + request.status());
        }

        Transfer transfer = Transfer.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .sendDate(request.sendDate())
                .receiveDate(request.receiveDate())
                .warehouseTo(warehouseTo)
                .warehouseFrom(warehouseFrom)
                .transferStatus(status)
                .build();

        transferRepository.save(transfer);
    }

    public TransferResponse getTransferById(Integer id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow(
                () -> new TransferNotFoundException("Transfer not found with id: " + id)
        );

        return TransferResponse.builder()
                .productId(transfer.getProductId())
                .quantity(transfer.getQuantity())
                .sendDate(transfer.getSendDate())
                .receiveDate(transfer.getReceiveDate())
                .warehouseFrom(WarehousesMapper.toResponse(transfer.getWarehouseFrom()))
                .warehouseTo(WarehousesMapper.toResponse(transfer.getWarehouseTo()))
                .transferId(transfer.getTransferId())
                .transferStatus(transfer.getTransferStatus().name())
                .build();
    }
}
