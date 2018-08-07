package fr.sacem.priam.rest.common.config;

import fr.sacem.fwk.config.Environment;
import fr.sacem.priam.security.model.UserDTO;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebMvc
@Profile("test")
public class RestMvcConfigTest extends WebMvcConfigurerAdapter {

    @Autowired
    private ResourceLoader resourceLoader;

    //@Value( "${webapp.mode}" )
    private String webappMode;

    @Value("${vuejs.server.url}")
    private String vuejsDevServerUrl;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("/");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        webappMode = Environment.getParameter("webapp.mode");

        if("dev".equalsIgnoreCase(webappMode)) {
            registry.addMapping("/app/rest/**")
                    .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                    .allowedOrigins("*");
        }

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {
                return UserDTO.class.equals(methodParameter.getParameterType());
            }

            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
                UserDTO userDTO = null;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(authentication instanceof TestingAuthenticationToken){
                    userDTO = (UserDTO) authentication.getPrincipal();
                }
                return userDTO == null ? UserDTO.GUEST : userDTO;
            }
        });
    }

    @Bean(name = "error")
    @Primary
    public View defaultErrorView() {
        return new View() {

            @Override
            public String getContentType() {
                return MediaType.TEXT_HTML_VALUE;
            }

            @Override
            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                if (response.getContentType() == null) {
                    response.setContentType(MediaType.TEXT_HTML_VALUE);
                }

                Object status = model.get("status");
                if (status != null &&
                        (status.equals(HttpStatus.SC_NOT_FOUND) ||
                                status.equals(HttpStatus.SC_FORBIDDEN) ||
                                status.equals(HttpStatus.SC_UNAUTHORIZED))) {
                    request.getServletContext().getRequestDispatcher("/404.html").forward(request, response);
                } else {
                    request.getServletContext().getRequestDispatcher("/error.html").forward(request, response);
                }

            }
        };
    }

}
