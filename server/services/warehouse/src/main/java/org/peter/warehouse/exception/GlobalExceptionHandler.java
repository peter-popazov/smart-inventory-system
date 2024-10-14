package org.peter.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransferNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransferNotFoundException(TransferNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder().error(exp.getMessage()).build()
                );
    }

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWarehouseNotFoundException(WarehouseNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder().error(exp.getMessage()).build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotValidExp(MethodArgumentNotValidException exp) {
        HashMap<String, String> errors = new HashMap<>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            String filedName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(filedName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder().errors(errors).build()
                );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exp) {
        // todo logger
        exp.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .businessErrorDesc("Internal Server Error")
                                .error(exp.getMessage())
                                .build()
                );
    }

}
