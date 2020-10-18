package com.products.api.Exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestValidationHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleValidationError(MethodArgumentNotValidException mNotArgumentNotValidException,
                                                                             HttpServletRequest request) {
        FieldValidationErrorDetails fValidationErrorDetails = new FieldValidationErrorDetails();
        fValidationErrorDetails.setError_timeStamp(new Date().getTime());
        fValidationErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fValidationErrorDetails.setError_title("Field Validation Error");
        fValidationErrorDetails.setError_detail("Inut Field Validation Failed");
        fValidationErrorDetails.setError_developer_Message(mNotArgumentNotValidException.getClass().getName());
        fValidationErrorDetails.setError_path(request.getRequestURI());

        BindingResult result = mNotArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors) {
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fValidationErrorsList = fValidationErrorDetails.getErrors().get(error.getField());
            if (fValidationErrorsList == null) {
                fValidationErrorsList = new ArrayList<FieldValidationError>();
            }
            fValidationErrorsList.add(fError);
            fValidationErrorDetails.getErrors().put(error.getField(), fValidationErrorsList);
        }                return new ResponseEntity<FieldValidationErrorDetails>(fValidationErrorDetails, HttpStatus.BAD_REQUEST);
    }

    // method to process field error
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
