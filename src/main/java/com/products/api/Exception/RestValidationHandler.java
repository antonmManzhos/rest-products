package com.products.api.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ControllerAdvice
public class RestValidationHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodNotReadableException(HttpMessageNotReadableException mNotArgumentNotValidException,
                                                                             HttpServletRequest request) {
        var fValidationErrorDetails = new FieldValidationErrorDetails();
        fValidationErrorDetails.setError_timeStamp(new Date().getTime());
        fValidationErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fValidationErrorDetails.setError_title("Date Field Validation Error");
        fValidationErrorDetails.setError_detail("Date Field Validation Failed");
        fValidationErrorDetails.setError_developer_Message(mNotArgumentNotValidException.getClass().getName());
        fValidationErrorDetails.setError_path(request.getRequestURI());

        FieldValidationError error = new FieldValidationError();
        error.setField("date");
        error.setMessage(mNotArgumentNotValidException.getMessage());
        error.setType(MessageType.ERROR);

        List<FieldValidationError> errorList = new ArrayList<>();
        errorList.add(error);
        fValidationErrorDetails.getErrors().put("date", errorList);

        return new ResponseEntity<>(fValidationErrorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleValidationError(MethodArgumentNotValidException mNotArgumentNotValidException,
                                                                             HttpServletRequest request) {

        FieldValidationErrorDetails fValidationErrorDetails = new FieldValidationErrorDetails();
        fValidationErrorDetails.setError_timeStamp(new Date().getTime());
        fValidationErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fValidationErrorDetails.setError_title("Field Validation Error");
        fValidationErrorDetails.setError_detail("Input Field Validation Failed");
        fValidationErrorDetails.setError_developer_Message(mNotArgumentNotValidException.getClass().getName());
        fValidationErrorDetails.setError_path(request.getRequestURI());

        BindingResult result = mNotArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors) {
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fValidationErrorsList = fValidationErrorDetails.getErrors().get(error.getField());
            if (fValidationErrorsList == null) {
                fValidationErrorsList = new ArrayList<>();
            }
            fValidationErrorsList.add(fError);
            fValidationErrorDetails.getErrors().put(error.getField(), fValidationErrorsList);
        }                return new ResponseEntity<>(fValidationErrorDetails, HttpStatus.BAD_REQUEST);
    }

    private FieldValidationError processFieldError(final FieldError error) {
        FieldValidationError fieldValidationError = new FieldValidationError();
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            fieldValidationError.setField(error.getField());
            fieldValidationError.setType(MessageType.ERROR);
            fieldValidationError.setMessage(msg);
        }
        return fieldValidationError;
    }
}
