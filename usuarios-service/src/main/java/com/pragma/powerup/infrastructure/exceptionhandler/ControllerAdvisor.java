package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidateExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fielName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();

            errors.put(fielName, message);
        });
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Map<String, String>>  handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        String error = "Error de integridad de datos UK";
        String message= ex.getLocalizedMessage();
        errors.put(error, message);
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }




}