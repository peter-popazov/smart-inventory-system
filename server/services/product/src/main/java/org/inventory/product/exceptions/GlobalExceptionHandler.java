package org.inventory.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundExp(CategoryNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder().error(exp.getMessage()).build()
                );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundExp(ProductNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder().error(exp.getMessage()).build()
                );
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundExp(InsufficientQuantityException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder().error(exp.getMessage()).build()
                );
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessExp(UnauthorizedAccessException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder().error(exp.getMessage()).build()
                );
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryNotFoundExceptionExp(InventoryNotFoundException exp) {
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
