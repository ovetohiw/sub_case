package sub.service.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    private Set<String> processedRequests = ConcurrentHashMap.newKeySet();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String uuid = servletRequest.getParameter("UUID");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }

        if (processedRequests.contains(uuid)) {
            logger.info("{} Duplicate request {} with UUID: {} ", request.getMethod(), request.getRequestURI(), uuid);
            return;
        }

        logger.info("{} Request {} with UUID: {} ", request.getMethod(), request.getRequestURI(), uuid);
        processedRequests.add(uuid);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            processedRequests.remove(uuid);
        }
    }
}
