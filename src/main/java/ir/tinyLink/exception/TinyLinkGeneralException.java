package ir.tinyLink.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@Getter
@AllArgsConstructor
public class TinyLinkGeneralException extends RuntimeException {

    private HttpStatus status;
    private String message;
}
