package ir.tinyLink.exception;


import ir.tinyLink.util.LocalizedMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TinyLinkGeneralException extends RuntimeException {

    private HttpStatus status;
    private String message;

//    @SneakyThrows
//    @Override
//    public String getMessage() {
//        return LocalizedMessages.getMessage(message, LocaleContextHolder.getLocale());
//    }



}
