package org.inventory.appuser.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundExp(UserNotFoundException exp) {
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

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .businessErrorDesc("Entered token not found")
                                .error(exp.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .businessErrorDesc("Entered token has expired. Check your email for a new one.")
                                .error(exp.getMessage())
                                .build()
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
