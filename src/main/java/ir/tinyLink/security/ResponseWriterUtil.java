package ir.tinyLink.security;

import ir.tinyLink.message.ErrorMessage;
import ir.tinyLink.model.dto.RestResponse;
import ir.tinyLink.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Component
public class ResponseWriterUtil {
    public void sendUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        RestResponse<Object> restResponse = RestResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .path((String) request.getAttribute("userUri"))
                .message(ErrorMessage.errorUnauthorized())
                .timestamp(new Date(Long.parseLong((String) Objects.requireNonNull(request.getAttribute("startDate")))))
                .build();
        response.setStatus(status.value());
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtil.getWithNoTimestampJson(restResponse));
    }

}
