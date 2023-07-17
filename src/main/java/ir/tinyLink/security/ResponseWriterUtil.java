package ir.tinyLink.security;

import ir.tinyLink.model.vo.RestResponse;
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
 * Created by z.gholinia on 2020/07/21 @PodBusinessPanel.
 */

@Component
public class ResponseWriterUtil {
    public void sendUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        RestResponse<Object> restResponse = new RestResponse<>(
                status.value(),
                status.getReasonPhrase(),
                (String) request.getAttribute("userUri"),
                "عدم اعتبار مجوز دسترسی",
                (String) request.getAttribute("referenceId"),
                new Date(Long.parseLong((String) Objects.requireNonNull(request.getAttribute("startDate"))))
        );
        response.setStatus(status.value());
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtil.getWithNoTimestampJson(restResponse));
    }

}
