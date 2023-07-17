package ir.tinyLink.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private HttpStatus status;
    private String content;
    private String reason;
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();
}
