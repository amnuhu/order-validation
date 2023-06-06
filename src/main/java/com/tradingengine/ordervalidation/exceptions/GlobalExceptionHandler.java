package com.tradingengine.ordervalidation.exceptions;


import com.tradingengine.ordervalidation.exceptions.validation.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

        @ExceptionHandler(IOException.class)
        public void handleElasticSearchIOException (IOException ex){
            log.info("An Error Occurred {}",ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BuyLimitException.class)
    public ResponseEntity<Error> handleBuyLimitException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                ex.getMessage(),
                ErrorCode.NOT_FOUND.getErrCode(),
                HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BuyPriceException.class)
    public ResponseEntity<Error> handleBuyPriceException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ErrorCode.NOT_FOUND.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Error> handleInsufficientException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ErrorCode.NOT_FOUND.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoStockException.class)
    public ResponseEntity<Error> handleNoStockException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ErrorCode.NOT_FOUND.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoWalletException.class)
    public ResponseEntity<Error> handleWalletException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ErrorCode.NOT_FOUND.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SellLimitException.class)
    public ResponseEntity<Error> handleSellLimitException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ErrorCode.NOT_FOUND.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SellPriceException.class)
    public ResponseEntity<Error> handleSellPriceException(HttpServletRequest request, Exception ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ErrorCode.NOT_FOUND.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURI())
                .setReqMethod(request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }





}

