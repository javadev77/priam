package fr.sacem.priam.common.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 24/03/15.
 */
public abstract class RemoteHealthIndicator implements HealthIndicator {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public final Health health() {
        Health.Builder builder = new Health.Builder();
        String healthUrl = healthServiceContext() + "/health";
        builder.withDetail("healthCheckUrl", healthUrl);
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> entity = restTemplate.exchange(healthUrl, HttpMethod.GET, request, String.class);
            convertToHealth(builder, entity.getStatusCode(), entity.getBody());
        }catch (Exception ex){
            if(ex instanceof HttpStatusCodeException){
                String responseBody = ((HttpStatusCodeException) ex).getResponseBodyAsString();
                convertToHealth(builder, ((HttpStatusCodeException) ex).getStatusCode(), responseBody);
            }else{
                builder.down(ex);
            }
        }
        return builder.build();
    }

    protected abstract String healthServiceContext();

    protected void convertToHealth(Health.Builder builder, HttpStatus status, String responseBody) {
        builder.withDetail("httpCode", status.getReasonPhrase());
        Map health = null;
        if(responseBody != null){
            try {
                health = jsonMapper.readValue(responseBody, Map.class);
            } catch (Exception e) {
                builder.withDetail("response", responseBody);
                builder.down(e);
                return;
            }
        }

        if(health != null){
            String statusCode = Status.UNKNOWN.getCode();
            String statusDescription = "";
            for(Object key : health.keySet()){
                String keyStr = String.valueOf(key);
                Object data = health.get(key);
                switch (keyStr){
                    case "status":
                        statusCode = String.valueOf(data);
                        break;
                    case "description":
                        statusDescription = String.valueOf(data);
                        break;
                    default:
                        builder.withDetail(keyStr, data);
                        break;
                }
            }
            builder.status(new Status(statusCode, statusDescription));
        }else{
            builder.withDetail("error", "got null response object");
            builder.down();
        }
    }


}
