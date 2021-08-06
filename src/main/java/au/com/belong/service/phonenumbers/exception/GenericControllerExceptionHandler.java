package au.com.belong.service.phonenumbers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GenericControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleNotFoundException(HttpServletRequest request, Exception exception) {
        return new ExceptionResponse(request.getRequestURI(), exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CanntActivateActivePhoneNumberException.class)
    public ExceptionResponse handleCantActivateActivePhoneNumberException(HttpServletRequest request, Exception exception) {
        return new ExceptionResponse(request.getRequestURI(), exception);
    }
}
