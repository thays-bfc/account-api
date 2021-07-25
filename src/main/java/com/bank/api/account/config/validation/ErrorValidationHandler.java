package com.bank.api.account.config.validation;

import com.bank.api.account.util.exceptions.AccountException;
import com.bank.api.account.util.exceptions.AccountTransactionException;
import com.bank.api.account.util.exceptions.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorValidationHandler {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormDTO> handle(MethodArgumentNotValidException exception) {
        List<ErrorFormDTO> errorFormDTOList = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e-> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorFormDTO error = new ErrorFormDTO(e.getField() + ": " + message);
            errorFormDTOList.add(error);
        });
        return errorFormDTOList;
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<Object> handle(CustomerException exception) {
        ErrorFormDTO error = new ErrorFormDTO(exception.getMessage());
        return new ResponseEntity<>(
                error, new HttpHeaders(), exception.getStatus());
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> handle(AccountException exception) {
        ErrorFormDTO error = new ErrorFormDTO(exception.getMessage());
        return new ResponseEntity<>(
                error, new HttpHeaders(), exception.getStatus());
    }

    @ExceptionHandler(AccountTransactionException.class)
    public ResponseEntity<Object> handle(AccountTransactionException exception) {
        String message = ObjectUtils.isEmpty(exception.getParams()) ? exception.getMessage() :
                String.format(exception.getMessage(), exception.getParams());
        ErrorFormDTO error = new ErrorFormDTO(message);
        return new ResponseEntity<>(
                error, new HttpHeaders(), exception.getStatus());
    }
}
