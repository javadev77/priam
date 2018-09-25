package fr.sacem.priam.common.interceptor;


import fr.sacem.priam.common.util.SsoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class WeSsoAuthCookieInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeSsoAuthCookieInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        SsoUtils.addSsoHeaders(LOGGER, request);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("calling webservice with request[{} {}, Headers={}]", request.getMethod(), request.getURI(), request.getHeaders());
        }

        ClientHttpResponse response = execution.execute(request, body);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("got webservice [{} {}] response = [{}]", request.getMethod(), request.getURI(), response.getStatusCode());
        }
        return response;
    }
}
