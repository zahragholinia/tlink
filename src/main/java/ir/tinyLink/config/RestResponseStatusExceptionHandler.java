package ir.tinyLink.config;

import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.ErrorMessage;
import ir.tinyLink.model.dto.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@Log4j2
@ControllerAdvice
public class RestResponseStatusExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({TinyLinkGeneralException.class})
    public ResponseEntity<?> businessPanelGeneralExceptionHandler(HttpServletRequest request, TinyLinkGeneralException exception) {

        return ResponseEntity
                .status(exception.getStatus().value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(exception.getStatus().value())
                        .path((String) request.getAttribute("userUri"))
                        .message(exception.getMessage())
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate")))

                        ).build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> runtimeExceptionHandler(HttpServletRequest request, RuntimeException exception) {
        request.setAttribute("stackTrace", ExceptionUtils.getStackTrace(exception));
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(status.value())
                        .error(exception.getLocalizedMessage())
                        .path((String) request.getAttribute("userUri"))
                        .message(ErrorMessage.errorInternalServer())
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate")))

                        ).build());
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> illegalArgumentException(HttpServletRequest request, RuntimeException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(status.value())
                        .error(exception.getLocalizedMessage())
                        .path((String) request.getAttribute("userUri"))
                        .message(ErrorMessage.errorValidation())
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate")))

                        ).build());
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<?> validationExceptionHandler(HttpServletRequest request, ValidationException exception) {
        int status = 400;
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(status)
                        .error(error)
                        .path((String) request.getAttribute("userUri"))
                        .message(this.getValidationErrorMessage(exception))
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate")))
                        ).build());
    }


    private ResponseEntity<Object> methodArgumentNotValidExceptionHandler(WebRequest request, MethodArgumentNotValidException exception) {
        int status = 400;
        StringBuilder message = new StringBuilder();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        if (errors.size() > 1) {
            message.append("[");
        }
        Iterator msgs = errors.iterator();
        while (msgs.hasNext()) {
            ObjectError error = (ObjectError) msgs.next();
            message.append(error.getDefaultMessage());
            if (msgs.hasNext()) {
                message.append(", ");
            }
        }
        if (errors.size() > 1) {
            message.append("]");
        }
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(status)
                        .error(error)
                        .path((String) request.getAttribute("userUri", 0))
                        .message(message.toString())
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate", 0))))
                        .build()

                );
    }

    private ResponseEntity<Object> methodArgumentTypeMismatchExceptionHandler(WebRequest request, MethodArgumentTypeMismatchException exception) {
        int status = 400;
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(status)
                        .error(error)
                        .path((String) request.getAttribute("userUri", 0))
                        .message(ErrorMessage.errorTypeField(exception.getName(), exception.getRequiredType().getSimpleName()))
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate", 0))))
                        .build()

                );
    }


    private ResponseEntity<Object> bindExceptionHandler(WebRequest request, BindException exception) {
        int status = 400;
        StringBuilder message = new StringBuilder();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        if (errors.size() > 1) {
            message.append("[");
        }
        Iterator msgs = errors.iterator();
        message.append(ErrorMessage.errorValidation()).append("\n");
        while (msgs.hasNext()) {
            ObjectError error = (ObjectError) msgs.next();
            message.append(((FieldError) error).getField()).append(error.getDefaultMessage()).append(")\n");
        }
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder()
                        .status(status)
                        .error(error)
                        .path((String) request.getAttribute("userUri", 0))
                        .message(message.toString())
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate", 0))))
                        .build()

                );
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            return this.methodArgumentNotValidExceptionHandler(request, (MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return this.bindExceptionHandler(request, (BindException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return this.methodArgumentTypeMismatchExceptionHandler(request, (MethodArgumentTypeMismatchException) ex);
        }
        ResponseEntity<Object> entity = super.handleExceptionInternal(ex, body, headers, status, request);
        return ResponseEntity
                .status(entity.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(RestResponse.builder().status(entity.getStatusCodeValue())
                        .error(entity.getStatusCode().getReasonPhrase())
                        .path((String) request.getAttribute("userUri", 0))
                        .message(ex.getMessage())
                        .timestamp(new Date(Long.parseLong((String) request.getAttribute("startDate", 0))))
                        .build()
                );
    }

    private String getValidationErrorMessage(ValidationException exception) {
        if (exception instanceof ConstraintViolationException) {
            return ErrorMessage.errorValidation();
        }
        return exception.getMessage();
    }

}