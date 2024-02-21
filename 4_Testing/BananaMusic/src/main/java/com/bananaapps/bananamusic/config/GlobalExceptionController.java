package com.bananaapps.bananamusic.config;

import com.bananaapps.bananamusic.domain.music.Song;
import com.bananaapps.bananamusic.exception.GlobalException;
import com.bananaapps.bananamusic.exception.ProductNotFoundException;
import com.bananaapps.bananamusic.exception.SongNotfoundException;
import com.bananaapps.bananamusic.exception.UserNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(GlobalException.class)
    ResponseEntity<Object> globalExceptionHandler(GlobalException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<Object> productNotFoundExceptionHandler(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @ExceptionHandler(SongNotfoundException.class)
    ResponseEntity<Object> songNotfoundExceptionHandler(SongNotfoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado");
    }

    @ExceptionHandler(UserNotfoundException.class)
    ResponseEntity<Object> userNotfoundException(UserNotfoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<Object> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Object> validationExceptionHandler(ValidationException exception) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(exception.getMessage());
    }
}
