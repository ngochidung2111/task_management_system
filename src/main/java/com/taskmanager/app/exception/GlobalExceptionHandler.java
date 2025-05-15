package com.taskmanager.app.exception;

import com.taskmanager.app.exception.MissingOrInvalidTokenException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {


    @ExceptionHandler(MissingOrInvalidTokenException.class)
    public ResponseEntity<Map<String,Object>> handleMissingToken(MissingOrInvalidTokenException ex) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("status", "401");
        body.put("message", ex.getMessage());
        body.put("data", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }





}
