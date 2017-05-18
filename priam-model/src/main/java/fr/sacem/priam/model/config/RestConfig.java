package fr.sacem.priam.model.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import fr.sacem.priam.model.util.StdDateFormatExt;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by benmerzoukah on 11/05/2017.
 */
@Configuration
public class RestConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestConfig.class);
  private static final Logger RESTLOGGER = LoggerFactory.getLogger("fr.sacem.priam.resttemplate");
  
  static{
    BeanUtilsBean.setInstance(new BeanUtilsBean(new ConvertUtilsBean(){
      @Override
      public Object convert(String value, Class clazz) {
        if (clazz.isEnum() && value != null) {
          String name = value.trim();
          if (hasText(name)) {
            return Enum.valueOf(clazz, name);
          }
        }
        return super.convert(value, clazz);
      }
    }));
  }
  
  
  @Bean
  @Primary
  public ObjectMapper jsonMapper() {
    LOGGER.info("Rest Config -- JsonMapper.");
    
    ObjectMapper objectMapper = new ObjectMapper();
    VisibilityChecker<?> visibilityChecker = objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
      .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
      .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
      .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
      .withIsGetterVisibility(JsonAutoDetect.Visibility.ANY)
      .withCreatorVisibility(JsonAutoDetect.Visibility.ANY);
    objectMapper.setVisibility(visibilityChecker);
    
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
    // extends default StdDateFormat by adding yyy-MM-dd HH:mm:ss format
    objectMapper.setDateFormat(new StdDateFormatExt());
  
    
    
    try {
      Class<?> module = Class.forName("com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module");
      objectMapper.registerModule((Module) module.newInstance());
    } catch (Exception e) {
      if(LOGGER.isDebugEnabled()){
        LOGGER.debug("Hibernate4Module is not activated.");
      }
    }
    return objectMapper;
  }
  
  @Bean @Lazy
  @Primary
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public RestTemplate restTemplate(ObjectMapper jsonMapper) {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    RestTemplate restTemplate ;
    if ( RESTLOGGER.isDebugEnabled() ) {
      restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
      //restTemplate.getInterceptors().add(new WeSsoAuthCookieInterceptor());
      //restTemplate.getInterceptors().add(new LoggingRequestInterceptor(RESTLOGGER));
    } else {
      restTemplate = new RestTemplate(requestFactory);
      //restTemplate.getInterceptors().add(new WeSsoAuthCookieInterceptor());
    }
    configureMessageConverter(jsonMapper, restTemplate);
    return restTemplate;
  }
  
  @Bean @Lazy @Primary @Order(Ordered.HIGHEST_PRECEDENCE)
  public AsyncRestTemplate asyncRestTemplate(RestTemplate restTemplate){
      SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
      requestFactory.setTaskExecutor(new SimpleAsyncTaskExecutor("PriamAsyncRest"));
      
      return new PriamAsyncRestTemplate(requestFactory, restTemplate);
  }
  
  public static class PriamAsyncRestTemplate extends AsyncRestTemplate {
    
      //private static final Logger LOGGER = LoggerFactory.getLogger(WeSsoAuthCookieInterceptor.class.getName() + ".Async");
      
      public PriamAsyncRestTemplate(SimpleClientHttpRequestFactory requestFactory, RestTemplate restTemplate) {
        super(requestFactory, restTemplate);
      }
      
      @Override
      protected AsyncClientHttpRequest createAsyncRequest(URI url, HttpMethod method) throws IOException {
        AsyncClientHttpRequest asyncRequest = super.createAsyncRequest(url, method);
        //SsoUtils.addSsoHeaders(LOGGER, asyncRequest);
        /*if(LOGGER.isDebugEnabled()){
          LOGGER.debug("async webservice call created with [{} {}, Headers={}]", asyncRequest.getMethod(), asyncRequest.getURI(), asyncRequest.getHeaders());
        }*/
        return asyncRequest;
      }
  }
  
  public static void configureMessageConverter(ObjectMapper jsonMapper, RestTemplate restTemplate) {
      List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
      
      boolean passJacksonMapper = true;
      int left = 2;
      Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
      while(iterator.hasNext() && left > 0){
        HttpMessageConverter<?> next = iterator.next();
        if(next instanceof MappingJackson2HttpMessageConverter){
          ((MappingJackson2HttpMessageConverter) next).setObjectMapper(jsonMapper);
          passJacksonMapper = false;
          left--;
        } else if (next instanceof FormHttpMessageConverter){
          iterator.remove();
          left--;
        }
      }
      if(passJacksonMapper){
        messageConverters.add(new MappingJackson2HttpMessageConverter(jsonMapper));
      }
      
      FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
      formHttpMessageConverter.addPartConverter(new MappingJackson2HttpMessageConverter(jsonMapper));
      messageConverters.add(formHttpMessageConverter);
  }
  
}
