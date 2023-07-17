package ir.tinyLink.config;

import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.model.vo.RestResponse;
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
import javax.validation.ValidationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by z.gholinia on 2020/07/21 @PodBusinessPanel.
 */

@Log4j2
@ControllerAdvice
public class RestResponseStatusExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({TinyLinkGeneralException.class})
    public ResponseEntity<?> businessPanelGeneralExceptionHandler(HttpServletRequest request, TinyLinkGeneralException exception) {

        return ResponseEntity
                .status(exception.getStatus().value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestResponse<>(
                        exception.getStatus().value(),
                        null,
                        (String) request.getAttribute("userUri"),
                        exception.getMessage(),
                        (String) request.getAttribute("referenceId"),
                        new Date(Long.parseLong((String) request.getAttribute("startDate")))
                ));
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> runtimeExceptionHandler(HttpServletRequest request, RuntimeException exception) {
        log.error("RuntimeExceptionHandler", exception);
        request.setAttribute("stackTrace", ExceptionUtils.getStackTrace(exception));
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        System.out.println("======== شروع خطا پنل ====================");
        System.out.println(exception.getMessage());
        System.out.println(exception.getStackTrace());
        System.out.println("======== پایان خطا پنل ====================");
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestResponse<>(
                        status.value(),
                        exception.getLocalizedMessage(),
                        (String) request.getAttribute("userUri"),
                        exception.getMessage(),
                        (String) request.getAttribute("referenceId"),
                        new Date(Long.parseLong((String) request.getAttribute("startDate")))
                ));
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> illegalArgumentException(HttpServletRequest request, RuntimeException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestResponse<>(
                        status.value(),
                        exception.getLocalizedMessage(),
                        (String) request.getAttribute("userUri"),
                        "مقادیر فیلدهای ورودی بررسی شود",
                        (String) request.getAttribute("referenceId"),
                        new Date(Long.parseLong((String) request.getAttribute("startDate")))
                ));
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<?> validationExceptionHandler(HttpServletRequest request, ValidationException exception) {
        int status = 400;
        String message = exception.getMessage();
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestResponse<>(
                        status,
                        error,
                        (String) request.getAttribute("userUri"),
                        exception.getMessage(),
                        (String) request.getAttribute("referenceId"),
                        new Date(Long.parseLong((String) request.getAttribute("startDate")))
                ));
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
                .body(new RestResponse<>(
                        status,
                        error,
                        (String) request.getAttribute("userUri", 0),
                        message.toString(),
                        (String) request.getAttribute("referenceId", 0),
                        new Date(Long.parseLong((String) request.getAttribute("startDate", 0)))
                ));
    }

    private ResponseEntity<Object> methodArgumentTypeMismatchExceptionHandler(WebRequest request, MethodArgumentTypeMismatchException exception) {
        int status = 400;
        String message = String.format("خطا: لطفا ورودی با نام '%s' را از جنس '%s' وارد کنید.", exception.getName(), exception.getRequiredType().getSimpleName());
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestResponse<>(
                        status,
                        error,
                        (String) request.getAttribute("userUri", 0),
                        message,
                        (String) request.getAttribute("referenceId", 0),
                        new Date(Long.parseLong((String) request.getAttribute("startDate", 0)))
                ));
    }


    private ResponseEntity<Object> bindExceptionHandler(WebRequest request, BindException exception) {
        int status = 400;
        StringBuilder message = new StringBuilder();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        if (errors.size() > 1) {
            message.append("[");
        }
        Iterator msgs = errors.iterator();
        message.append("خطا: مقادیر ورودی فیلد‌های زیر بررسی شود").append("\n");
        while (msgs.hasNext()) {
            ObjectError error = (ObjectError) msgs.next();
            message.append(((FieldError) error).getField()).append(" با پیغام خطا (").append(error.getDefaultMessage()).append(")\n");
        }
        String error = "Bad Request";
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestResponse<>(
                        status,
                        error,
                        (String) request.getAttribute("userUri", 0),
                        message.toString(),
                        (String) request.getAttribute("referenceId", 0),
                        new Date(Long.parseLong((String) request.getAttribute("startDate", 0)))
                ));
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
                .body(new RestResponse<>(
                        entity.getStatusCodeValue(),
                        entity.getStatusCode().getReasonPhrase(),
                        (String) request.getAttribute("userUri", 0),
                        ex.getMessage(),
                        (String) request.getAttribute("referenceId", 0),
                        new Date(Long.parseLong((String) Objects.requireNonNull(request.getAttribute("startDate", 0))))
                ));
    }

}