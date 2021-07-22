package com.bank.api.account.config.validation;

import com.bank.api.account.util.exceptions.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerException.class)
    public List<ErrorFormDTO> handle(CustomerException exception) {
        List<ErrorFormDTO> errorFormDTOList = new ArrayList<>();
        ErrorFormDTO error = new ErrorFormDTO(exception.getMessage());
        errorFormDTOList.add(error);

        return errorFormDTOList;
    }
}
