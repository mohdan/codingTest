package com.mytaxi.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter
{

    private static final Log LOG = LogFactory.getLog(LoggingInterceptor.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);


    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object)
        throws Exception
    {
        final String uuid = UUID.randomUUID().toString();
        LOGGER.info("UUID: [{}] - Inside preHandle of RequestInterceptor. Received [{}] on URI [{}].", uuid,
            request.getMethod(), request.getRequestURI());

        request.setAttribute(UtilityConstants.UUID_CONSTANT, uuid);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("method: ").append(request.getMethod()).append("\t");
        logMessage.append("uri: ").append(request.getRequestURI()).append("\t");
        logMessage.append("status: ").append(response.getStatus()).append("\t");
        logMessage.append("remoteAddress: ").append(request.getRemoteAddr()).append("\t");

        if (ex != null)
        {
            LoggingInterceptor.LOG.error(logMessage.toString(), ex);
        }
        else
        {
            LoggingInterceptor.LOG.info(logMessage.toString());
        }

    }

}
