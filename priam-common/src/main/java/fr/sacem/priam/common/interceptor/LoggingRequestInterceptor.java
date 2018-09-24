package fr.sacem.priam.common.interceptor;

import org.slf4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Created by Guillaume on 01/06/2016.
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    public final Logger logger ;

    public LoggingRequestInterceptor(Logger logrequest) {
        this.logger = logrequest;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        logger.debug("===========================request begin================================================");
        logger.debug("URI : " + request.getURI());
        logger.debug("Method : " + request.getMethod());
        for (Map.Entry<String, List<String>> header : request.getHeaders().entrySet() ) {
            for ( String headerLine : header.getValue() ) {
                logger.debug(header.getKey()+": " + headerLine);
            }
        }
        logger.debug("\n" + new String(body, "UTF-8"));
        logger.debug("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        logger.debug("============================response begin==========================================");
        logger.debug("status code: " + response.getStatusCode());
        logger.debug("status text: " + response.getStatusText());
        for (Map.Entry<String, List<String>> header : response.getHeaders().entrySet() ) {
            for ( String headerLine : header.getValue() ) {
                logger.debug(header.getKey()+": " + headerLine);
            }
        }
        logger.debug("Response Body : " + inputStringBuilder.toString());
        logger.debug("=======================response end=================================================");
    }
}
