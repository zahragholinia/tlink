package ir.tinyLink.security;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by z.gholinia on 2020/07/21 @PodBusinessPanel.
 */

@Component
@Order(Integer.MIN_VALUE + 10)
@Priority(1)
public class AfterMatchingFilter implements Filter {

    @Value("${server.version.name:podBusinessPanel}")
    private String PANEL_SEVER_NAME;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String timestamp = String.valueOf(System.currentTimeMillis());
        servletRequest.setAttribute("startDate", timestamp);
        servletRequest.setAttribute("referenceId", generateReferenceId(timestamp));
        RequestFacade facade = (RequestFacade) servletRequest;
        String method = facade.getMethod();
        String requestURI = facade.getRequestURI();
        servletRequest.setAttribute("userUri", method + " " + requestURI);

        filterChain.doFilter(servletRequest, servletResponse);

    }

    public String generateReferenceId(String timestamp) {
        UUID prefix = UUID.nameUUIDFromBytes(timestamp.getBytes());
        return prefix.toString().concat("-").concat(PANEL_SEVER_NAME);
    }
}
